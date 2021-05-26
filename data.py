import pandas as pd
from xlwt import Workbook
import random 

wb = Workbook()
sheet1 = wb.add_sheet('sheet 1')

sheet1.write(0, 0, 'STT')
sheet1.write(0, 1, 'X axis')
sheet1.write(0, 2, 'Y axis')
sheet1.write(0, 3, 'Weight')

for i in range(100):
    sheet1.write(i + 1, 0, i)
    sheet1.write(i + 1, 1, random.randint(1, 1000))
    sheet1.write(i + 1, 2, random.randint(1, 1000))
    if ((i == 1) or (i == 8) or (i == 9)):
        sheet1.write(i + 1, 3, 2)
    elif ((i == 23) or (i == 72) or (i == 29) or (i == 67) or (i == 55)):
        sheet1.write(i + 1, 3, 3)
    elif ((i == 4) or (i == 48)):
        sheet1.write(i + 1, 3, 5)
    else:
        sheet1.write(i + 1, 3, 1)
wb.save('vertiex_data.xls')

