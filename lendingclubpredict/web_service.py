import pandas as pd
import json

from sklearn.externals import joblib
from flask import Flask, request, jsonify

app = Flask(__name__)

clf = joblib.load('gradient_boosting_model.pkl') 

print(clf)

@app.route("/predictDefault", methods=['GET', 'POST'])
def predictDefault():

    content = request.get_json()
    
    print("Requisicao: \n", json.dumps(content, indent=2))
    
    
#    df = pd.DataFrame({
#                       'int_rate'    : 18.55,	
#                       'tot_cur_bal' : 26055.0,
#                       'grade'       : 4,
#                       'dti'         : 25.82,                       
#                       'revol_bal'   : 10908.0,
#                       'revol_util'  : 74.70,
#                       'annual_inc'  : 56000.0,
#                       'loan_amnt'   : 9750.0,
#                       'total_acc'   : 21.0
#                      },
#                     index=[0])
    
    
    df = pd.DataFrame(content, index=[0])
    df = df[[
            'int_rate', 
#             'tot_cur_bal', 
             'grade', 
#             'dti', 
#             'revol_bal', 
#             'revol_util', 
             'annual_inc', 
             'loan_amnt', 
#             'total_acc'
             ]]
    
    print(df)
    
    print('Chamando Predict ')
    
    predicted_class = clf.predict(df)
    probas_classes = clf.predict_proba(df)
    
    print('Predicted class: {0}'.format(predicted_class))
    print('Probabilities per class: {0}'.format(probas_classes))
    
    return jsonify(
        predicted_class=("good" if predicted_class[0].item() == 1 else "delinquent"),
        delinquent_prob=probas_classes[0][0].item()*100
    )

if __name__ == "__main__":
    app.run()