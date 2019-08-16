# About this project
This project is a  a program that keeps a record of position. Each position includes a currency and an amount. The program should output a list of all the currency and amounts to the console once per minute. The input can be typed into the command line, and optionally also be loaded from a file when starting up.
## Sample input:
USD 1000 <br/> 
HKD 100 <br/> 
USD -100 <br/> 
CNY 2000 <br/> 
HKD 200 <br/> 
## Sample output:
USD 900 <br/> 
CNY 2000 <br/> 
HKD 300 <br/> 
# Prepare Environmet
You need have maven and Java installed in your system.

# Start Application
1. clone the repo and cd repo folder

2. `mvn clean package`

3. `java -jar target/CurrencyAccount-1.0-SNAPSHOT.jar [input_file_path]`

# How to run tests
`mvn test`