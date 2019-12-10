Feature: Basic Commands

  Scenario: The help command
    Given a Symphony user types "?help"
    When a Symphony user sends the message
    Then Symphony should display the following response
      """
      broker-dealer algo bot - created using ALgo Framework (ALF) managed by Symphony

      COMMANDS
        ?help - baseline methods
        ?whoami - describes the roll you have in the room to the bot
        ?setclientuser - sets the user who can interact with the bot
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
  Scenario: The bot is the same room as the room administrator
    Given a Symphony user types "?whoami"
      And the user is the administrator of the room
     When a Symphony user sends the message
     Then Symphony should display the following response
      """
        The room administrator - which allows you to set the client users who can interact with this bot.
        To set their status please use $> ?setclientuser @mention-the-user
      """

  Scenario: The bot can only communicate with authorised users
    Given a Symphony user types "?whoami"
      And the user is not the administrator of the room
      And the user is not set as a client user
     When a Symphony user sends the message
     Then Symphony should display the following response
      """
        Please speak to the room admin to enable you to interact with me
      """

  Scenario: The bot can communicate to an authorised user ie client user
    Given a Symphony user types "?whoami"
      And the user is not the administrator of the room
      And the user is set as a client user
     When a Symphony user sends the message
     Then Symphony should display the following response
      """
        Client user - please type ?help to see the commands available to you
      """

  Scenario: A room admin can set an authorised client user
    Given a Symphony user types "?setclientuser @Ron Burgundy"
      And the user is the administrator of the room
     When a Symphony user sends the message
     Then Symphony should display the following response
      """
        @Ron Burgundy has been set as an authorised Client user
      """

