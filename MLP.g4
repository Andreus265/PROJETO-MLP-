grammar MLP;

inicio: '$' tipo* comando* '$.' ;

tipo: 'inteiro' IDENTIFICADOR (',' IDENTIFICADOR)* ';'
    | 'real' IDENTIFICADOR (',' IDENTIFICADOR)* ';'
    | 'caracter' IDENTIFICADOR (',' IDENTIFICADOR)* ';'
    ;

comando: condicional
       | iterativo
       | atribuicao
       ;

condicional: 'se' condicao 'entao' comando ';'
           | 'se' condicao 'entao' comando 'senao' comando ';'
           ;

iterativo: 'enquanto' condicao comando ';' ;

atribuicao: IDENTIFICADOR '=' (expressao | IDENTIFICADOR | FRASE) (operador (expressao | IDENTIFICADOR))* ';' ;

condicao: '(' IDENTIFICADOR logico (IDENTIFICADOR | NUMERO) ')' (('E' | 'OR') '(' IDENTIFICADOR logico (IDENTIFICADOR | NUMERO) ')')*
        | 'NOT' '(' IDENTIFICADOR logico (IDENTIFICADOR | NUMERO) (('E' | 'OR') IDENTIFICADOR logico (IDENTIFICADOR | NUMERO)*)* ')'
        ;

expressao: NUMERO
         | '(' expressao operador expressao ')' ;

IDENTIFICADOR: LETRAS (LETRAS | DIGITO)* ;
NUMERO: DIGITO+ ('.' DIGITO+)? | '.' DIGITO+ ;
FRASE: '"' .*? '"';
LETRAS: [a-zA-Z]+ ;
DIGITO: [0-9] ;
logico: '>' | '<' | '==' | '<=' | '>=' | '!=' ;
operador: '+' | '-' | '*' | '/' | 'RESTO' ;

// Ignora espaços em branco
WS: [ \t\r\n]+ -> skip ;

