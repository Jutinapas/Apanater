Feature: เพิ่มห้อง
    As a ผู้จัดการหอพัก I want to เพิ่มห้องพักตาม ชื่อห้องพัก ชั้น และประเภทของห้องพักตามประเภทที่ได้กำหนดไว้

Background:
    Given มีประเภทห้องที่ชื่อประเภทเป็น อยู่ดีกินดี และราคาค่าเช่ารายเดือนเป็น 3000 ราคาค่าเช่ารายวันเป็น 300

Scenario:
    When กดเพิ่มห้อง จากนั้นใส่ชื่อห้องเป็น 101 ชั้น 1 และประเภทเป็นอยู่ดีกินดี แล้วกดตกลง
    Then มีห้องใหม่ และจำนวนห้องเพิ่มขึ้น 1