@cucumber
Feature:  Test demo excel

  @TC_123
  Scenario Outline: TC_123 - write multiples user by excel
    Given ready actor
    And actualizar Excel resultado , <id> y fallido
    And read Excel <id>
    When writeUser
    Then actualizar Excel resultado , <id> y exitoso
    Examples:
      | id |
      | 1  |
      | 2  |
      | 3  |

