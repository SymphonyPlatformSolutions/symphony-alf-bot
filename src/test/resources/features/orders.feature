@valid_mic_codes @config
Feature: Orders Command
    The ?orders command is intended to list out orders of various characteristics
    For example you can list orders that are:
    * out of limit using
    $> ?orders <mic_code> <ool|orders out of limit>
    * market on close using
    $> ?orders <mic_code> <moc|market on close>

  Further information on MIC codes
  https://www.iso20022.org/10383/iso-10383-market-identifier-codes

  Scenario: The orders command with no parameters should display the help for that command
    Given a Symphony user types "?orders"
    When a Symphony user sends the message
    Then Symphony should display the following response
      """
      The ?orders command is intended to list out orders of various characteristics from a particular market
      For example you can list orders that are:
      * out of limit using
      $> ?orders <mic_code> <ool|orders out of limit>
      * market on close using
      $> ?orders <mic_code> <moc|market on close>
      """

  Scenario: The orders market on close short command with no MIC code
    Given a Symphony user types "?orders ool"
    When a Symphony user sends the message
    Then Symphony should display the following response
      """
      Please provide a valid MIC code to identify the market.
      """
    And Symphony should display the correct "orders" help message

  Scenario: The open limit orders short command with invalid MIC code
    Given a Symphony user types "?orders WRONG ool"
    When a Symphony user sends the message
    Then Symphony should display the following response
      """
      Please provide a valid MIC code to identify the market.
      """

  Scenario: The orders market on close short command with invalid MIC code
    Given a Symphony user types "?orders WRONG moc"
    When a Symphony user sends the message
    Then Symphony should display the following response
      """
      Please provide a valid MIC code to identify the market.
      """

  # the criteria to detect open orders is down to the developer to determine, this acts as a template
  Scenario: Testing the orders open limit orders short command with valid MIC code
    Given a Symphony user types "?orders XHKG ool"
    And I have the following open limit orders
      | instrument type | instrument     | instrument short name | limit | last  | volume   | open  | order id            | side | mic  |
      | BBERG           | 700 HK Equity  | TENCENT               | 331   | 331   | 5491925  | 330.8 | ORHK000000000002457 | B    | XHKG |
      | BBERG           | 3328 HK Equity | BANKCOMM-H            | 5.17  | 5.10  | 8916666  | 5.17  | ORHK000000000002463 | S    | XHKG |
      | BBERG           | 386 HK Equity  | SINOPEC CORP-H        | 4.41  | 4.41  | 31856472 | 4.4   | ORHK000000000002461 | B    | XHKG |
      | BBERG           | 5 HK Equity    | HSBC HOLDINGS PL      | 58.2  | 58.2  | 4857091  | 58.2  | ORHK000000000002456 | S    | XHKG |
      | BBERG           | 857 HK Equity  | PETROCHINA-H          | 3.62  | 3.63  | 34802702 | 3.63  | ORHK000000000002460 | S    | XHKG |
      | BBERG           | DBS SP Equity  | DBS GROUP HLDGS       | 25.0  | 25.20 | 70945    | 25.40 | ORHK000000000005463 | B    | XSES |
    When a Symphony user sends the message
    Then Symphony should display the following response
      | instrument short name | instrument type | instrument     | side | limit | last  | volume   | order id            |
      | TENCENT               | BBERG           | 700 HK Equity  | B    | 331   | 331   | 5491925  | ORHK000000000002457 |
      | SINOPEC CORP-H        | BBERG           | 386 HK Equity  | B    | 4.41  | 4.41  | 31856472 | ORHK000000000002461 |
      | HSBC HOLDINGS PL      | BBERG           | 5 HK Equity    | S    | 58.2  | 58.2  | 4857091  | ORHK000000000002456 |
      | PETROCHINA-H          | BBERG           | 857 HK Equity  | S    | 3.62  | 3.63  | 34802702 | ORHK000000000002460 |
      | BANKCOMM-H            | BBERG           | 3328 HK Equity | S    | 5.17  | 5.10  | 8916666  | ORHK000000000002463 |

  # the criteria to detect market on close orders is down to the developer to determine, this acts as a template
  Scenario: The orders market on close short command with valid MIC code
    Given a Symphony user types "?orders XHKG ool"
    And I have the following market on close orders
      | instrument type | instrument     | instrument short name | limit | last  | volume   | open  | order id            | side | mic  |
      | BBERG           | 700 HK Equity  | TENCENT               | 331   | 331   | 5491925  | 330.8 | ORHK000000000002457 | B    | XHKG |
      | BBERG           | 3328 HK Equity | BANKCOMM-H            | 5.17  | 5.10  | 8916666  | 5.17  | ORHK000000000002463 | S    | XHKG |
      | BBERG           | 386 HK Equity  | SINOPEC CORP-H        | 4.41  | 4.41  | 31856472 | 4.4   | ORHK000000000002461 | B    | XHKG |
      | BBERG           | 5 HK Equity    | HSBC HOLDINGS PL      | 58.2  | 58.2  | 4857091  | 58.2  | ORHK000000000002456 | S    | XHKG |
      | BBERG           | 857 HK Equity  | PETROCHINA-H          | 3.62  | 3.63  | 34802702 | 3.63  | ORHK000000000002460 | S    | XHKG |
      | BBERG           | DBS SP Equity  | DBS GROUP HLDGS       | 25.0  | 25.20 | 70945    | 25.40 | ORHK000000000005463 | B    | XSES |
    When a Symphony user sends the message
    Then Symphony should display the following response
      | instrument type | instrument     | instrument short name | limit | last | short sell shares | volume   | open  | order id            | side |
      | BBERG           | 700 HK Equity  | TENCENT               | 331   | 331  | 1744600           | 5491925  | 330.8 | ORHK000000000002457 | B    |
      | BBERG           | 3328 HK Equity | BANKCOMM-H            | 5.17  | 5.10 | 758000            | 8916666  | 5.17  | ORHK000000000002463 | S    |
      | BBERG           | 386 HK Equity  | SINOPEC CORP-H        | 4.41  | 4.41 | 10066000          | 31856472 | 4.4   | ORHK000000000002461 | B    |
      | BBERG           | 5 HK Equity    | HSBC HOLDINGS PL      | 58.2  | 58.2 | 1844400           | 4857091  | 58.2  | ORHK000000000002456 | S    |
      | BBERG           | 857 HK Equity  | PETROCHINA-H          | 3.62  | 3.63 | 8524000           | 34802702 | 3.63  | ORHK000000000002460 | S    |
