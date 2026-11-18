*** Settings ***
Resource         ../resources/desk.resource
Test Setup       Start Desk
Test Teardown    Close Desk
Test Tags        regression    tracking

*** Test Cases ***
View Booked Shipment
    [Tags]    smoke
    Select Abu Dhabi
    Result Should Be    SH-101 | Booked

View Delivered Shipment
    Select Dubai
    Result Should Be    SH-202 | Delivered

Advance Booked Shipment
    [Tags]    smoke
    Select Abu Dhabi
    Advance Shipment
    Result Should Be    SH-101 | In transit

Record Complete Shipment History
    Select Abu Dhabi
    Advance Shipment
    Advance Shipment
    Show History
    Result Should Be    SH-101 | Booked > In transit > Delivered

Prevent Duplicate Delivery Event
    Select Dubai
    Advance Shipment
    Result Should Be    SH-202 | Already delivered
    Show History
    Result Should Be    SH-202 | Booked > In transit > Delivered

Keep Shipments Independent
    Select Abu Dhabi
    Advance Shipment
    Select Dubai
    Result Should Be    SH-202 | Delivered
    Select Abu Dhabi
    Result Should Be    SH-101 | In transit
