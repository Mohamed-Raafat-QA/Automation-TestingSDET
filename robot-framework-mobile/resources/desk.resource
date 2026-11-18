*** Settings ***
Library    AppiumLibrary    timeout=12    run_on_failure=No Operation
Library    OperatingSystem

*** Keywords ***
Start Desk
    ${platform}=    Get Environment Variable    PLATFORM
    IF    $platform.lower() == "android"
        ${driver}=    Set Variable    UiAutomator2
    ELSE IF    $platform.lower() == "ios"
        ${driver}=    Set Variable    XCUITest
    ELSE
        Fail    PLATFORM must be Android or iOS
    END
    Open Application    %{APPIUM_URL=http://127.0.0.1:4723}
    ...    platformName=${platform}    automationName=${driver}
    ...    app=%{APP_PATH}    udid=%{DEVICE_UDID}    deviceName=%{DEVICE_NAME=Parcel Desk device}
    ...    noReset=${False}
    Wait Until Page Contains Element    accessibility_id=result

Close Desk
    Run Keyword If Test Failed    Run Keyword And Ignore Error    Capture Page Screenshot
    Close All Applications

Click Control
    [Arguments]    ${id}
    Wait Until Page Contains Element    accessibility_id=${id}
    Click Element    accessibility_id=${id}

Result Should Be
    [Arguments]    ${expected}
    Wait Until Keyword Succeeds    12s    250ms    Check Result    ${expected}

Check Result
    [Arguments]    ${expected}
    ${actual}=    Get Text    accessibility_id=result
    Should Be Equal As Strings    ${actual}    ${expected}

Open Shipments
    Click Control    tracking

Open Checkout
    Click Control    checkout

Select Abu Dhabi
    Click Control    parcel_a

Select Dubai
    Click Control    parcel_b

Advance Shipment
    Click Control    advance

Show History
    Click Control    history

Add Notebook
    Click Control    add

Remove Notebook
    Click Control    remove

Apply Save10
    Click Control    voucher

Select Express
    Click Control    express

Place Order
    Click Control    order
