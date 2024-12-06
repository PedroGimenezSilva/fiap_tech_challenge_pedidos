# language: pt
Funcionalidade: Pedido

  Cenario: Criar pedido
    Quando crio um pedido
    Então vejo que o pedido foi criado

  Cenario: Não listo pedidos no estado inicial
    Dado que tenho um pedido criado no status inicial
    Quando eu consulto a lista de pedidos
    Então o pedido não está listado

  Cenario: Listo pedidos recebidos
    Dado que tenho um pedido criado no status inicial
    Quando atualizo o seu estado para o estado recebido
    E eu consulto a lista de pedidos
    Então o pedido está listado