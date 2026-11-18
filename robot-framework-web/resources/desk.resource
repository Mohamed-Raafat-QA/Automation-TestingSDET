*** Settings ***
Library    SeleniumLibrary    timeout=12    run_on_failure=No Operation

*** Keywords ***
Start Desk
    ${directory}=    Set Variable    ${CURDIR}
    ${url}=    Evaluate    pathlib.Path($directory, "../demo/index.html").resolve().as_uri()    modules=pathlib
    ${options}=    Evaluate    selenium.webdriver.ChromeOptions()    modules=selenium.webdriver
    ${binary}=    Set Variable    %{CHROME_BIN=}
    IF    $binary
        Evaluate    setattr($options, "binary_location", $binary)
    END
    Call Method    ${options}    add_argument    --headless\=new
    Call Method    ${options}    add_argument    --window-size\=1280,900
    Open Browser    ${url}    chrome    options=${options}
    Wait Until Element Is Visible    id=result

Close Desk
    Run Keyword If Test Failed    Run Keyword And Ignore Error    Capture Page Screenshot
    Close All Browsers

Click Control
    [Arguments]    ${id}
    Wait Until Element Is Enabled    id=${id}
    Click Element    id=${id}

Result Should Be
    [Arguments]    ${expected}
    Wait Until Keyword Succeeds    12s    250ms    Element Text Should Be    id=result    ${expected}

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
