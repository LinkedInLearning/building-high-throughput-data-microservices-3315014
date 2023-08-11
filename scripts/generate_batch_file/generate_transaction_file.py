# Generate test file
# @author Gregory Green
from datetime import datetime

count = 2000001

f = open("runtime/transactions.csv", "w")

f.write("\"id\",\"details\",\"contact\",\"location\",\"amount\",\"timestamp\"\n")

for i in range(1, count):
    ts = datetime.now()

    amount = i * 1.0
    csv = "\""+str(i)+"\""+",\"Transaction"+str(i)+"\",\"contact"+str(i)+"\",\"location"+str(i)+"\",\""+str(amount)+"\",\""+str(ts)+"\"\n"
    f.write(csv)


f.close()