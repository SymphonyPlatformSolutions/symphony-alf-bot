Feature: Basic Commands

  Background:
    Given there is a valid configuration for "broker-dealer"

  Scenario: Testing the help command
    When a user types "?help"
    Then the bot should reply with the help text
      """
      broker-dealer algo bot - created using ALgo Framework (ALF) managed by Symphony

      COMMANDS
        ?help - baseline methods
        ?orders <mic_code> <ool|orders out of limit>
        ?orders <mic_code> <moc|market on close>
        ?ordstatus <order_id>
        ?ordstatus <mic_code> <instrument> <instrument_type>
        ?market <mic_code> <+> top 5 instruments with a positive movement on the day
        ?market <mic_code> <-> top 5 instruments with a negative movement on the day
        ?market <mic_code> <ss> top 5 largest short sell moves of the day
        ?market <mic_code> <vol> top 5 largest moves by volume on the day

      For more information please visit: http://github.com/....
      """
