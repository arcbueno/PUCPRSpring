# Projeto Final - Parte 1
- Para a primeira entrega, foi realizada a criação de 2 modelos/domínios: Dish e DishCategory
- A idéia é similar a um app de delivery como iFood/Rappi que se tem as categorias dos pratos dentro de um restaurante, em que vários pratos podem fazer parte de uma mesma categoria
- Para os endpoints de Dish, foi feito a filtragem por ASC/DESC e por nome de categoria
- Para os endpoints de DishCategory, foi criado um endpoint para criação e outro para adicionar/remover pratos 

O vídeo de explicação do código está disponível neste link
https://drive.google.com/drive/folders/1zCF7qgSeuqzjBcNpiTpN9L9tybrBJLWE?usp=sharing

# Projeto Final - Parte 2
- Foi implementado a camada de seguraça para os dois domínios de Dish e DishCategory
- Para Dish, foi criado um novo endpoint POST /dishes/search que deve validar se o usuário tem o role de USER ou ADMIN
    - Se ADMIN, ele exibe todos os pratos
    - Se USER, ele exibe apenas os pratos ativos
- Além disso, em Dish e em DishCategory, endpoint de DELETE foram limitados a ADMIN apenas
- Também em Dish, só se é possível criar pratos se for ADMIN

O vídeo de explicação do código está disponível no link no youtube abaixo:
https://youtu.be/GZxvA4c3AVs