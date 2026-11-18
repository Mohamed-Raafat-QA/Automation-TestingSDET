*** Settings ***
Library    AppiumLibrary    timeout=12    run_on_failure=No Operation
Library    OperatingSystem

Resource    tracking.resource
Resource    checkout.resource

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

Open Shipments
    Click Control    tracking

Open Checkout
    Click Control    checkout
