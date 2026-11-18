*** Settings ***
Library    AppiumLibrary    timeout=12    run_on_failure=No Operation

*** Keywords ***
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
