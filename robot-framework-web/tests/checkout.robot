*** Settings ***
Resource         ../resources/desk.resource
Test Setup       Start Desk
Test Teardown    Close Desk
Test Tags        regression    checkout

*** Test Cases ***
Block Empty Checkout
    Open Checkout
    Place Order
    Result Should Be    Add an item before ordering

Price Two Notebooks
    Open Checkout
    Add Notebook
    Add Notebook
    Result Should Be    Items 2 | Total AED 80

Remove Last Item
    Open Checkout
    Add Notebook
    Remove Notebook
    Result Should Be    Items 0 | Total AED 0
    Remove Notebook
    Result Should Be    Items 0 | Total AED 0

Apply Voucher Once
    Open Checkout
    Add Notebook
    Apply Save10
    Result Should Be    Items 1 | Total AED 36
    Apply Save10
    Result Should Be    Items 1 | Total AED 36

Exclude Delivery From Discount
    Open Checkout
    Add Notebook
    Select Express
    Apply Save10
    Result Should Be    Items 1 | Total AED 48

Confirm Order And Clear Cart
    [Tags]    smoke
    Open Checkout
    Add Notebook
    Place Order
    Result Should Be    Order confirmed | Paid AED 40
    Open Checkout
    Result Should Be    Items 0 | Total AED 0
    Place Order
    Result Should Be    Add an item before ordering

Retain Basket While Tracking
    Open Checkout
    Add Notebook
    Open Shipments
    Select Dubai
    Result Should Be    SH-202 | Delivered
    Open Checkout
    Result Should Be    Items 1 | Total AED 40

Reset Voucher And Delivery After Payment
    Open Checkout
    Add Notebook
    Select Express
    Apply Save10
    Place Order
    Result Should Be    Order confirmed | Paid AED 48
    Add Notebook
    Result Should Be    Items 1 | Total AED 40
