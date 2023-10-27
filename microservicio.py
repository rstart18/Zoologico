from flask import Flask, request, jsonify
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.application import MIMEApplication
from pymongo import MongoClient
from bson import ObjectId
from datetime import datetime

app = Flask(__name__)

client = MongoClient("mongodb://localhost:27017/")
db = client["zoo"]
collection = db["replies"]

email_address = 'taringa_01@outlook.com'
email_password = 'taringa01'

@app.route('/mail-replie-of-comment', methods=['POST'])
def enviar_correo():
    data = request.get_json()

    recipient_email = data.get('receiver')
    subject = data.get('emitterName') + " acaba de responder tu comentario."
    body = "El comentario que habias publicado ha sido respondido: \n\n" + data.get('replie')

    msg = MIMEMultipart()
    msg['From'] = email_address
    msg['To'] = recipient_email
    msg['Subject'] = subject

    msg.attach(MIMEText(body, 'plain'))

    server = smtplib.SMTP('smtp-mail.outlook.com', 587)
    server.starttls()
    server.login(email_address, email_password)

    server.sendmail(email_address, recipient_email, msg.as_string())

    server.quit()

    data['timestamp'] = datetime.now()

    result = collection.insert_one(data)

    return ('', 204)

if __name__ == '__main__':
    app.run()
