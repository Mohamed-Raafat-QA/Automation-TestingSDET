*** Settings ***
Library    SeleniumLibrary    timeout=12    run_on_failure=No Operation

*** Keywords ***
Click Control
    [Arguments]    ${id}
    Wait Until Element Is Enabled    id=${id}
    Click Element    id=${id}

Result Should Be
    [Arguments]    ${expected}
    Wait Until Keyword Succeeds    12s    250ms    Element Text Should Be    id=result    ${expected}
