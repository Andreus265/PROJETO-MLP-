#!/bin/bash

# Configurando aliases
alias antlr4='java -jar /usr/local/lib/antlr/antlr-4.13.0-complete.jar'
alias grun='java -cp .:/usr/local/lib/antlr/antlr-4.13.0-complete.jar org.antlr.v4.gui.TestRig'

# Compilando arquivos Java
javac -cp .:/usr/local/lib/antlr/antlr-4.13.0-complete.jar *.java

# Executando o programa principal
java Main
