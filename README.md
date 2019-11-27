# Project Name - KU CINEMA 
![](https://github.com/folkdogfk/KUcinema/blob/master/src/com/gn/module/media/logo-mini.png)
# สมาชิกในกลุ่ม

| รหัสนิสิต  | ชื่อ  | github username |
| ------------ | ------------ | ------------ | 
| 6010450012 | นาย กรกช ไตรวิเชียร | korrakot1396 |
| 6010450586 | นาย ภัทรพล พลตะคุ | folkdogfk |
| 6010450713 | นาย ศิขริน กาดีโรจน์ | sirzelos |
| 6010450381 | นาย นิพัทธ์ ปาลสินกุลกิจ | nipathm |

## คำอธิบายโปรเจค
โปรเจคนี้เป็นการทำระบบจองที่นั่งในโรงหนังขนาดเล็กของมหาวิทยาลัย ชื่อว่า KU-Cinema เพื่อให้นิสิตหรือบุคลากรที่เป็นสมาชิกของระบบสามารถจองที่นั่งเพื่อเข้าไปชมภาพยนตร์ได้
และสมาชิกของระบบ สามารถตรวจสอบที่นั่ง ตรวจสอบภาพยนตร์ที่จะเข้าฉาย หรือกำลังทำการฉายอยู่ และสามารถจองที่นั่งได้ ส่วน ADMIN ของระบบสามารถทำการเพิ่มหนังได้ สามารถทำการเพิ่มรอบหนังได้ 
## Open Source Theme 
โดย Project นี่ได้มีการใช้งาน Open Source Theme DashBordFX https://github.com/Gleidson28/DashboardFx เข้ามาเพื่อเพิ่มความสวยงาม โดย การทำงานของระบบของ KU CINEMA จะอยุ่ที่ PACKAGE com/gn/module/movie , com/gn/objects , com/gn/Database 

## ขั้นตอนการติดตั้งโปรเจค
1. clone โปรเจคมาจาก github ลิ้งค์ https://github.com/folkdogfk/KUcinema.git
2. เมื่อ clone เสร็จเรียบร้อยให้ เปิดโปรแกรม Laragon 
3. คลิ๊กขวาที่ MySQL และกด Start MySQL
4. ต่อไป คลิ๊กขวาที่ MySQL อีกครั้ง และกด HeidiSQL
5. จะแสดงหน้าของ Database ของ Laragon ขึ้นมา
6. Open SQL file ขึ้นมาจากโปรเจคที่ clone มา Folder DatabaseSQL และ เลือก db.sql 
7. เปิดโปรแกรม InteliJ ขึ้นมา
8. เลือก Import Project จาก KUcinema 
9. กด Run 
10. ระบบ KUcinema พร้อมใช้งาน


