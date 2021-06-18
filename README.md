# Team-B P1: WebApp

## Description

This is a banking Java web application that allows users to persist their user information, crerate multiiple types of accounts, and perform trarnsactions including depositing and withrdrawing from their account balances. It uses a PostgreSQL database and a custom ORM to peresist user information and is exposed to client requests using HTTP servlets. This app was built in conjunction with a custom ORM which can be found [here](https://github.com/210426-java-react-enterprise/Team-B_p1_ORM).

## Endpoints

Please see below for information on the fields included in each type of JSON object.
All endpoints are prefixed with /bankapp/, e.g., /bankapp/users/

### /users

GET: Requires no parameters or body, returns the user information of the current user.
POST: Takes in a JSON Bank User object, validates username and email are available, and registers a new user. Request body must include a username of 1-15 characters, a password of 1-72 characters, a properly formatted email of 50 or less characters characters, a first name of 1-50 characters, and a last name of 1-50 characters.
PUT: Takes in a JSON Bank User object and updates the current user's information to that user object.
DELETE: Requires the current user's username and password to be passed in the request body, and if the credentials are valid, deletes the user from the database.

### /auth

POST: Takes in a JSON object with the user's username and password, verifies the information, and if correct, logs in that user to a session. On successful authentication it returns the user's credentials.

### /accounts

#### /accounts/types
GET: Takes in nothing and returns a list of account types.

#### /accounts/balance
GET: Takes in a JSON Account Balance and returns the current balance for that account.

#### /accounts/transactions
GET: Takes in a JSON Account object and returns a list of that account's transactions.

#### /accounts/newaccount
POST: Takes in a JSON Account object, saves that account, and returns the saved account information.

#### /accounts/deposit
POST: Takes in a JSON Withdraw/Deposit object, deposits into the specified account, and returns the new account balance.

#### /accounts/withdraw
POST: Takes in a JSON Withdraw/Deposit object, withdraws from the specified account, and returns the new account balance.

#### /accounts/transaction
POST: Takes in a JSON Account Transaction object and saves that transaction.

#### /accounts/*
DELETE: Functionality not currently supported; returns a default error message.

## Models

Fields in *italics* are response-only fields that should not be passed in a request.

### Bank User 
*uID*: integer, desc: the user's id

fName: string, desc: the user's first name

lName: string, desc: the user's last name

uName: string, desc: the user's username

email: string, desc: the user's email

password: string. desc: the user's password

### Credentials
username: string, desc: the user's username

password: string, desc: the user's password

### Account
*aID*: integer, desc: the account id

aName: string, desc: the account name

uID: integer, desc: the user id for the user creating the account

jUID: integer, desc: a separate user id to create a joint account (optional)

tID: integer, desc: the id for the type of account being created

### Account Balance
aID: integer, desc: the account id

*balance*: double, desc: the current balance for that account


### Account Transaction
acctID: integer, desc: the account id

*transactionID*: integer, desc: the id for the account transaction

description: string, desc: a description for the transaction

transactionAmt: double, desc: the amount for the transaction

### Account Type
*id*: integer, desc: the id for the account type

monthlyFees: double, desc: the monthly fees associated with this account type

interest: double, desc: the interest rate for this account type

type: string, desc: the name for this account type

### Withdraw/Deposit
aID: integer, desc: the id for the account being deposited to or withdrawn from

amount: double, desc: the amount to add to the associated account
