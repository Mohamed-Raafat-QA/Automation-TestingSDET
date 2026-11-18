*** Settings ***
Resource         ../resources/desk.resource
Test Setup       Start Desk
Test Teardown    Close Desk
Test Tags        regression    checkout

*** Test Cases ***
Block Empty Checkout
    Open Checkout
    checkout.Place Order
    Result Should Be    Add an item before ordering

Price Two Notebooks
    Open Checkout
    checkout.Add Notebook
    checkout.Add Notebook
    Result Should Be    Items 2 | Total AED 80

Remove Last Item
    Open Checkout
    checkout.Add Notebook
    checkout.Remove Notebook
    Result Should Be    Items 0 | Total AED 0
    checkout.Remove Notebook
    Result Should Be    Items 0 | Total AED 0

Apply Voucher Once
    Open Checkout
    checkout.Add Notebook
    checkout.Apply Save10
    Result Should Be    Items 1 | Total AED 36
    checkout.Apply Save10
    Result Should Be    Items 1 | Total AED 36

Exclude Delivery From Discount
    Open Checkout
    checkout.Add Notebook
    checkout.Select Express
    checkout.Apply Save10
    Result Should Be    Items 1 | Total AED 48

Charge Express Once After Voucher
    Open Checkout
    checkout.Add Notebook
    checkout.Apply Save10
    checkout.Select Express
    Result Should Be    Items 1 | Total AED 48
    checkout.Select Express
    Result Should Be    Items 1 | Total AED 48

Confirm Order And Clear Cart
    [Tags]    smoke
    Open Checkout
    checkout.Add Notebook
    checkout.Place Order
    Result Should Be    Order confirmed | Paid AED 40
    Open Checkout
    Result Should Be    Items 0 | Total AED 0
    checkout.Place Order
    Result Should Be    Add an item before ordering

Retain Basket While Tracking
    Open Checkout
    checkout.Add Notebook
    Open Shipments
    tracking.Select Dubai
    Result Should Be    SH-202 | Delivered
    Open Checkout
    Result Should Be    Items 1 | Total AED 40

Reset Voucher And Delivery After Payment
    Open Checkout
    checkout.Add Notebook
    checkout.Select Express
    checkout.Apply Save10
    checkout.Place Order
    Result Should Be    Order confirmed | Paid AED 48
    checkout.Add Notebook
    Result Should Be    Items 1 | Total AED 40
