# IceFactoryProject
Project นี้จัดทำขึ้นเพื่อช่วยเหลือด้านการเก็บข้อมูลสินค้า ข้อมูลOrder และ ข้อมูลของCustomer ของโรงงานผลิตน้ำแข็ง รวมไปถึงอำนวยความสะดวกในการเรียกดูและตรวจสอบข้อมูล เช่น การสรุปยอดขายของสินค้าชนิดต่างๆ การติดตามสถานะของorder การสร้างbill และ การสรุปรายงานยอดขายประจำวัน เป็นต้น

## วิธีการลง และ รันโปรแกรม 
-  ดาวน์โหลด โฟลเดอร์ IceFactoryApplication
-  ดับเบิ้ลคลิก iceFactoryApplication.jar ที่อยู่ใน โฟลเดอร์ IceFactoryApplication
-  หากดับเบิ้ลคลิกไม่ได้ ให้รันคำสั่ง cmd โดย directory ที่ทำการรันคำสั่งต้องเป็นตำแหน่งเดียวกันกับที่เก็บ iceFactoryApplication.jar รันคำสั่ง 
```
          		java -jar iceFactoryApplication.jar
```  
-  **Owner role**         USERNAME : owner / PASSWORD : 0000
-  **Staff role**         USERNAME : Staff / PASSWORD : 0000
-  หากมีการแก้ไข API ให้มีการรันใน docker ให้เปลี่ยน uri ใน src/main/java/iceFactory/IceFactoryApplication/service/IceFactoryAPIService.java ด้วยการ comment/uncomment

## สมาชิกกลุ่ม วิชา System Analysis and Design (01418321)
- นายพชร     ศรีสมบูรณ์โชติ  6210401295
- นายพีรวิชญ์   ตัณฑเวชกิจ   6210400167
- นายวงศกร 	  ปิ่นวาสี 		   6210400175

## สมาชิกกลุ่ม วิชา Introduction to Software Engineering (01418471)
- นายพชร    ศรีสมบูรณ์โชติ   6210401295
- นายพีรวิชญ์ ตัณฑเวชกิจ     6210400167
- นายธนณัฏฐ์ โกเมศจามิกรณ์  6210406581
- นายพีรดนย์ อชินีทองคำ     6210402461
