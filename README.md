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
#Assumptions
The following behaviors will cause errors. And an error message will be displayed and quit from the program.
1. If the user enters not 3 letters currency code.
2. If the operation make the amount of one currency less than 0.
3. If the input amount is not valid number
4. If the input has more than two words
5. If the input file provided but cannot be found by the program
# Prepare Environmet
You need have maven and Java installed in your system.

# Start Application
1. clone the repo and cd repo folder

2. `mvn clean package`

3. `java -jar target/CurrencyAccount-1.0-SNAPSHOT.jar [input_file_path]`

# How to run tests
`mvn test`