*** Settings ***
Resource         ../resources/desk.resource
Test Setup       Start Desk
Test Teardown    Close Desk
Test Tags        regression    tracking

*** Test Cases ***
View Booked Shipment
    [Tags]    smoke
    tracking.Select Abu Dhabi
    Result Should Be    SH-101 | Booked

View Delivered Shipment
    tracking.Select Dubai
    Result Should Be    SH-202 | Delivered

Advance Booked Shipment
    [Tags]    smoke
    tracking.Select Abu Dhabi
    tracking.Advance Shipment
    Result Should Be    SH-101 | In transit

Record Complete Shipment History
    tracking.Select Abu Dhabi
    tracking.Advance Shipment
    tracking.Advance Shipment
    tracking.Show History
    Result Should Be    SH-101 | Booked > In transit > Delivered

Prevent Duplicate Delivery Event
    tracking.Select Dubai
    tracking.Advance Shipment
    Result Should Be    SH-202 | Already delivered
    tracking.Show History
    Result Should Be    SH-202 | Booked > In transit > Delivered

Keep Shipments Independent
    tracking.Select Abu Dhabi
    tracking.Advance Shipment
    tracking.Select Dubai
    Result Should Be    SH-202 | Delivered
    tracking.Select Abu Dhabi
    Result Should Be    SH-101 | In transit
