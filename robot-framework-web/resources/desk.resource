*** Settings ***
Library    SeleniumLibrary    timeout=12    run_on_failure=No Operation

Resource    tracking.resource
Resource    checkout.resource

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

Open Shipments
    Click Control    tracking

Open Checkout
    Click Control    checkout
