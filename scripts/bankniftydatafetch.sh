#!/bin/bash

# added below crontab -e
# */1 9-15 * * * cd /Users/pmg/sourcecode/options/scripts && ./niftydatafetch.sh
# */1 9-15 * * * cd /Users/pmg/sourcecode/options/scripts && ./bankniftydatafetch.sh


current_date=$(date "+%Y.%m.%d")
current_time=$(date "+%Y.%m.%d-%H.%M.%S")

dir_path=/Users/pmg/Documents/bankNiftyData/$current_date
mkdir -p $dir_path

curl -o ${dir_path}/${current_time}_niftybank.json 'https://groww.in/v1/api/option_chain_service/v1/option_chain/nifty-bank'

curl -o ${dir_path}/${current_time}_niftybank_ohlc.json 'https://groww.in/v1/api/stocks_data/v1/accord_points/exchange/NSE/segment/CASH/latest_indices_ohlc/BANKNIFTY'

curl -o ${dir_path}/${current_time}_niftybank_derivatives.json 'https://groww.in/v1/api/option_chain_service/v1/option_chain/derivatives/nifty-bank'
