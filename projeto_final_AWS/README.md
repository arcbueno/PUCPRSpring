# Projeto Final AWS

**Disciplina:** Serviços Mobile em Cloud AWS </br>
**Professor:** Vinicius Godoy  </br>
**Aluno:** Pedro Augusto Reis Corrêa Bueno  </br>
**Link da apresentação:** https://youtu.be/dqyniy770FU

Projeto final escolhido: Avatares avançados
- Nenhum usuário merece ficar sem avatar. Por isso, o servidor fará um esforço para definir um:
  - Inicialmente, procure um avatar global no serviço gravatar: https://docs.gravatar.com/general/images
  - Caso ele não tenha um gravatar por lá, acione a API https://ui-avatars.com para gerar as letras com base no nome do usuário. Gere o avatar em png.
- O avatar encontrado deve ser baixado e enviado para o S3. Não basta só gravar o link dessas APIs do campo avatar do usuário (funcionaria, mas não é a ideia do trabalho)
- Opcional: Crie um endpoint DELETE user/{id}/avatar que refaz esse processo.
