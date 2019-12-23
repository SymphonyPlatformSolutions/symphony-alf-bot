@valid_mic_codes @config
Feature: Market Command
    The ?market command is intended to list out information on the market
    For example you can list
    * the top 5 positive movers
      $> ?market <mic_code> <+>
    * the top 5 negative movers
      $> ?market <mic_code> <->
    * the top 5 short sellers
      $> ?market <mic_code> <ss>
    * the top 5 by volume
      $> ?market <mic_code> <vol>

  Further information on MIC codes
  https://www.iso20022.org/10383/iso-10383-market-identifier-codes

  Background:
    Given there are the following orders in the System
      | instrument type | instrument     | instrument short name | bid   | ask   | last | short sell shares | volume    | open  | order id            | shortsell notional | sstop5 | voltop5 | percentage change | top5+ | top5- |
      | BBERG           | 700 HK Equity  | TENCENT               | 330.8 | 331   | 331  | 1744600           | 5491925   | 330.8 | ORHK000000000002457 | 577462600          |        |         | 0.9               | 1     |       |
      | BBERG           | 3328 HK Equity | BANKCOMM-H            | 5.16  | 5.17  | 5.17 | 758000            | 8916666   | 5.17  | ORHK000000000002463 | 3918860            |        |         | 0.19              | 2     |       |
      | BBERG           | 386 HK Equity  | SINOPEC CORP-H        | 4.4   | 4.41  | 4.41 | 10066000          | 31856472  | 4.4   | ORHK000000000002461 | 44391060           | 4      | 5       | -0.22             | 3     |       |
      | BBERG           | 5 HK Equity    | HSBC HOLDINGS PL      | 58.15 | 58.2  | 58.2 | 1844400           | 4857091   | 58.2  | ORHK000000000002456 | 107344080          |        |         | -0.25             | 4     |       |
      | BBERG           | 857 HK Equity  | PETROCHINA-H          | 3.62  | 3.63  | 3.63 | 8524000           | 34802702  | 3.63  | ORHK000000000002460 | 30942120           | 5      | 4       | -0.27             | 5     |       |
      | BBERG           | 939 HK Equity  | CCB-H                 | 6.25  | 6.26  | 6.26 | 55385000          | 165703432 | 6.25  | ORHK000000000002459 | 346710100          | 1      | 1       | -0.31             |       | 5     |
      | BBERG           | 1398 HK Equity | ICBC-H                | 5.63  | 5.64  | 5.64 | 28942000          | 104306295 | 5.63  | ORHK000000000002458 | 163232880          | 3      | 2       | -0.52             |       | 4     |
      | BBERG           | 3988 HK Equity | BANK OF CHINA-H       | 3.14  | 3.15  | 3.14 | 42276000          | 103848479 | 3.15  | ORHK000000000002462 | 132746640          | 2      | 3       | -0.62             |       | 3     |
      | BBERG           | 2318 HK Equity | PING AN               | 88.3  | 88.35 | 88.3 | 5158500           | 15444096  | 88.3  | ORHK000000000002465 | 455495550          |        |         | -0.93             |       | 2     |
      | BBERG           | 941 HK Equity  | CHINA MOBILE          | 59.1  | 59.15 | 59.1 | 1540000           | 10204517  | 59.15 | ORHK000000000002464 | 91014000           |        |         | -1.63             |       | 1     |

  Scenario: Market command with no parameters should display the help for that command
    Given a Symphony user types "?market"
    When a Symphony user sends the message in a room
    Then The bot should display the following response
      """
        The ?market command lists the top five by referenced market names with:
        - a positive move on the day
        - a negative move on the day
        - large short sells on the day
        - the most volume

        For example you can list orders that are:
        * the top 5 positive movers
        $> ?market <mic_code> <+>
        * the top 5 negative movers
        $> ?market <mic_code> <->
        * the top 5 short sellers
        $> ?market <mic_code> <ss>
        * the top 5 by volume
        $> ?market <mic_code> <vol>
      """

  # The following scenario tests the display only, the developer should supply more meaningful criteria for their own use cases.
  # The below serve as a template
  Scenario: Market command with the top 5 positive movers
    Given a Symphony user types "?market XHKG +"
    When a Symphony user sends the message in a room
    Then The bot should display the following response
      | instrument type | instrument short name | instrument     | open  | bid   | ask   | last | volume   | % change |
      | BBERG           | TENCENT               | 700 HK Equity  | 330.8 | 330.8 | 331   | 331  | 5491925  | 0.9      |
      | BBERG           | BANKCOMM-H            | 3328 HK Equity | 5.17  | 5.16  | 5.17  | 5.17 | 8916666  | 0.19     |
      | BBERG           | SINOPEC CORP-H        | 386 HK Equity  | 4.4   | 4.4   | 4.41  | 4.41 | 31856472 | -0.22    |
      | BBERG           | HSBC HOLDINGS PL      | 5 HK Equity    | 58.2  | 58.15 | 58.2  | 58.2 | 4857091  | -0.25    |
      | BBERG           | PETROCHINA-H          | 857 HK Equity  | 3.63  | 3.62  | 3.63  | 3.63 | 34802702 | -0.27    |

  Scenario: Market command with the top 5 negative movers
    Given a Symphony user types "?market XHKG -"
    When a Symphony user sends the message in a room
    Then The bot should display the following response
      | instrument type | instrument short name | instrument     | open  | bid   | ask   | last | volume    | % change |
      | BBERG           | CCB-H                 | 939 HK Equity  | 6.25  | 6.25  | 6.26  | 6.26 | 165703432 | -0.31    |
      | BBERG           | ICBC-H                | 1398 HK Equity | 5.63  | 5.63  | 5.64  | 5.64 | 104306295 | -0.52    |
      | BBERG           | BANK OF CHINA-H       | 3988 HK Equity | 3.15  | 3.14  | 3.15  | 3.14 | 103848479 | -0.62    |
      | BBERG           | PING AN               | 2318 HK Equity | 88.3  | 88.3  | 88.35 | 88.3 | 15444096  | -0.93    |
      | BBERG           | CHINA MOBILE          | 941 HK Equity  | 59.15 | 59.1  | 59.15 | 59.1 | 10204517  | -1.63    |

  Scenario: Market command with the top 5 short sold
    Given a Symphony user types "?market XHKG ss"
    When a Symphony user sends the message in a room
    Then The bot should display the following response
      | instrument type | instrument short name | instrument     | open  | bid   | ask   | last | volume    | short sell shares | shortsell notional | percentage change |
      | BBERG           | CCB-H                 | 939 HK Equity  | 6.25  | 6.25  | 6.26  | 6.26 | 165703432 | 55385000          | 346710100          | -0.31             |
      | BBERG           | ICBC-H                | 1398 HK Equity | 5.63  | 5.63  | 5.64  | 5.64 | 104306295 | 28942000          | 163232880          | -0.52             |
      | BBERG           | BANK OF CHINA-H       | 3988 HK Equity | 3.15  | 3.14  | 3.15  | 3.14 | 103848479 | 42276000          | 132746640          | -0.62             |
      | BBERG           | SINOPEC CORP-H        | 386 HK Equity  | 4.4   | 4.4   | 4.41  | 4.41 | 31856472  | 10066000          | 44391060           | -0.22             |
      | BBERG           | PETROCHINA-H          | 857 HK Equity  | 3.63  | 3.62  | 3.63  | 3.63 | 34802702  | 8524000           | 30942120           | -0.27             |

  Scenario: Market command with the top 5 by vol
    Given a Symphony user types "?market XHKG vol"
    When a Symphony user sends the message in a room
    Then The bot should display the following response
      | instrument type | instrument short name | instrument     | open  | bid   | ask   | last | volume    | percentage change |
      | BBERG           | CCB-H                 | 939 HK Equity  | 6.25  | 6.25  | 6.26  | 6.26 | 165703432 | -0.31             |
      | BBERG           | BANK OF CHINA-H       | 3988 HK Equity | 3.15  | 3.14  | 3.15  | 3.14 | 103848479 | -0.62             |
      | BBERG           | ICBC-H                | 1398 HK Equity | 5.63  | 5.63  | 5.64  | 5.64 | 104306295 | -0.52             |
      | BBERG           | SINOPEC CORP-H        | 386 HK Equity  | 4.4   | 4.4   | 4.41  | 4.41 | 31856472  | -0.22             |
      | BBERG           | PETROCHINA-H          | 857 HK Equity  | 3.63  | 3.62  | 3.63  | 3.63 | 34802702  | -0.27             |
