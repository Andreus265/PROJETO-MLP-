
#!/bin/bash

# Configurar o CLASSPATH para incluir o ANTLR
export CLASSPATH=".:/usr/local/lib/antlr/antlr-4.13.0-complete.jar:$CLASSPATH"

# Criar alias para facilitar o uso do ANTLR e do TestRig
alias antlr4='java -jar /usr/local/lib/antlr/antlr-4.13.0-complete.jar'
alias grun='java -cp .:/usr/local/lib/antlr/antlr-4.13.0-complete.jar org.antlr.v4.gui.TestRig'

# Executar o ANTLR no arquivo name.g4
antlr4 MLP.g4

# Mensagem de conclusão
echo "Configuração concluída e arquivo 'name.g4' processado."

 
