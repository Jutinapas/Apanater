Feature: เพิ่มประเภทห้อง
    As a ผู้จัดการหอพัก I want to เพิ่มข้อมูลประเภทห้อง และราคาค่าเช่า ทั้งแบบรายเดือน และรายวันในหอพักได้

Scenario:
    When กดเพิ่มประเภท จากนั้นใส่ชื่อประเภทเป็น อยู่ดีกินดี และราคาค่าเช่ารายเดือนเป็น 3000 ราคาค่าเช่ารายวันเป็น 300 แล้วกดตกลง
    Then มีประเภทห้องใหม่ชื่อประเภทเป็น อยู่ดีกินดี และราคาค่าเช่ารายเดือนเป็น 3000 ราคาค่าเช่ารายวันเป็น 300 และจำนวนประเภทห้องเพิ่มขึ้น