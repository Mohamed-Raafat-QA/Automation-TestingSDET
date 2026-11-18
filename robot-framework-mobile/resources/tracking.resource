*** Settings ***
Resource    controls.resource

*** Keywords ***
Select Abu Dhabi
    Click Control    parcel_a

Select Dubai
    Click Control    parcel_b

Advance Shipment
    Click Control    advance

Show History
    Click Control    history
