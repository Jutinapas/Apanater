Feature: แสดงรายการแจ้งหนี้ค้างชำระ
    As a ผู้จัดการหอพัก I want to เรียกดูรายการแจ้งหนี้ค้างชำระของห้องพักที่ยังไม่ได้ชำระเงินค่าเช่าได้ ตามประเภทห้อง และชื่อห้องที่กำหนด

Background:
    Given a มีการจองของห้อง 201 แบบรายวัน ตั้งแต่วันที่ 2018-12-01 ถึง 2018-12-15 โดยลูกค้าคือ นายกอ และเบอร์โทรศัพท์เป็น 001

Scenario:
    When ดูรายการแจ้งหนี้ค้างชำระ
    Then มีหนี้ค้างชำระของห้อง 201 อยู่ 1