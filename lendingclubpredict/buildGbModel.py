#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 30 10:00:49 2018

@author: diegosoaresub
"""

import pandas as pd
import util
import locale

from sklearn.ensemble import GradientBoostingClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from sklearn.externals import joblib

pd.set_option('max_columns', 120)
pd.set_option('max_colwidth', 5000)
pd.set_option('display.float_format', lambda x: locale.format('%.2f', x, grouping=True))

locale.setlocale(locale.LC_ALL, 'pt_br.utf-8')

loans = pd.read_csv('../processed_data/loan_2007_2016Q4_old.csv', low_memory=False)

loans = loans[[
        'loan_status',
        'int_rate',
#        'tot_cur_bal',
        'grade',
        'dti',
#        'revol_bal',
#        'revol_util',
        'annual_inc',
        'loan_amnt',
#        'total_acc'
]];

#Balanced data
loans_defaulted = loans[loans.loan_status == 0];
loans_ok = loans[loans.loan_status == 1].head(len(loans_defaulted))
loans = pd.concat([loans_ok, loans_defaulted])

X = loans.drop("loan_status", axis=1)
Y = loans.loan_status

loans = loans.drop("loan_status", axis=1)

x_train, x_test, y_train, y_test = train_test_split(X,Y,test_size=0.20)

# Create a random forest Classifier. By convention, clf means 'Classifier'
clf = GradientBoostingClassifier(n_estimators=200,                                      
                                 random_state=0, max_depth=10)  

# Train the Classifier to take the training features and learn how they relate
# to the training y (the species)
clf.fit(x_train, y_train)

print('Salvando modelo')
joblib.dump(clf, 'gradient_boosting_model.pkl') 
print('Modelo salvo')

predict = clf.predict(x_test)

print("Mean Squared Error: ", mean_squared_error(y_true = y_test, y_pred = predict))

acc = util.confusion_matrix_report(['Default', 'Good'], predict, y_test)
print("Acuracia: ", acc)