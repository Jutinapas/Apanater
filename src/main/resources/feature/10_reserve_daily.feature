Feature: เพิ่มการจอง
    As a ผู้จัดการหอพัก I want to เพิ่มการจองห้องพักแบบรายวันได้

Background:
    Given a มีห้อง 101 102 และ 201

Scenario:
    When กดเพิ่มการจองจากห้อง 201 แบบรายวัน ตั้งแต่วันที่ 2018-12-01 ถึง 2018-12-15 โดยลูกค้าคือ นายกอ และเบอร์โทรศัพท์เป็น 001
    Then มีการจองใหม่ และจำนวนการจองของห้อง 201 เพิ่มขึ้น 1