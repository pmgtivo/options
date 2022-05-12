#!/bin/bash

current_date=$(date "+%Y.%m.%d")
current_time=$(date "+%Y.%m.%d-%H.%M.%S")
dir_path=/Users/pmg/Documents/niftyData/$current_date
mkdir -p $dir_path

curl -o ${dir_path}/${current_time}_nifty.json 'https://groww.in/v1/api/option_chain_service/v1/option_chain/nifty'

curl -o ${dir_path}/${current_time}_nifty_ohlc.json 'https://groww.in/v1/api/stocks_data/v1/accord_points/exchange/NSE/segment/CASH/latest_indices_ohlc/NIFTY'

curl -o ${dir_path}/${current_time}_nifty_derivatives.json 'https://groww.in/v1/api/option_chain_service/v1/option_chain/derivatives/nifty'