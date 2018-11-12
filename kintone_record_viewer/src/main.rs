#[macro_use] extern crate serde_derive;
#[macro_use] extern crate prettytable;

extern crate base64;
extern crate reqwest;
extern crate serde_json;
extern crate toml;

use prettytable::Table;
use reqwest::{Client, StatusCode};
use std::fs;
use std::io::{BufReader, Read};

#[derive(Debug, Deserialize)]
struct Setting {
    setting: Items
}

#[derive(Debug, Deserialize)]
struct Items {
    domain: String,
    username: String,
    password: String,
    app_id: String,
    record_id: String,
}

fn read_file(path: String) -> Result<String, String> {
    let mut file_content = String::new();

    let mut fr = fs::File::open(path)
        .map(|f| BufReader::new(f))
        .map_err(|e| e.to_string())?;

    fr.read_to_string(&mut file_content)
        .map_err(|e| e.to_string())?;

    Ok(file_content)
}

fn main() {

    let setting_file_content = match read_file("./Setting.toml".to_owned()) {
        Ok(r) => r,
        Err(e) => panic!("Failed to read file: {}", e),
    };

    let setting_file : Result<Setting, toml::de::Error> = toml::from_str(&setting_file_content);
    let Items {domain, username, password, app_id, record_id} = setting_file.unwrap().setting;

    let url = ("https://".to_string() + domain.trim() + "/k/v1/record.json?app=" + app_id.trim() + "&id=" + record_id.trim()).to_string();
    let base64encoded = base64::encode(&(username.trim().to_string() + ":" + password.trim()));

    let client = Client::new();
    let resp = client.get(&url)
        .header("X-Cybozu-Authorization", base64encoded)
        .send()
        .unwrap();
    match resp.status() {
        StatusCode::OK => println!("\nSuccess!"),
        s => println!("Received response status: {:?}", s),
    };

    let mut table = Table::new();
    let resp_parsed :serde_json::Value = serde_json::from_reader(resp).unwrap();
    for item in resp_parsed["record"].as_object().unwrap() {
        let (field, value) = item;
        let value = &value["value"];

        match &value {
            serde_json::Value::String(string) => {
                table.add_row(row![Fy -> &field, Fy -> string]);
            },
            serde_json::Value::Object(object) => {
                table.add_row(row![Fy -> &field, Fy -> object["code"]]);
            },
            serde_json::Value::Array(array) => {
                table.add_row(row![Fy -> &field, Fy -> format!("[ Row number : {}]", array.len())]);
            },
            _ => println!("???"),
        }
    }
    table.printstd();
}
