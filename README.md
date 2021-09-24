# Las Flipantes Aventuras de Marco y Luis

Primero que todo y antes de empezar... 

Si bien el nombre de 
esta historia suena prometedor, los verdaderos protagonistas
son **Marcos** y **Luis**. Ellos son dos hermanos que buscan
nuevas aventuras.

<div style="text-align: center;">
    <b>¡Y tú has elegido para ayudarlos en su búsqueda!</b>
</div>

## Personajes

Para que Marcos y Luis puedan aventurarse en el Reino Frijol,
país vecino del Reino Champiñón, ellos deberán enfrentarse distintos
enemigos.

Por lo que le será útil saber cuáles serán las estadísticas de los
jugadores y enemigos en cuestión.

### Estadísticas
1. **Nivel (LVL)**: Corresponde al nivel del personaje, que permitirá 
subir el resto de estadísticas (como ATK, DEF, HP entre otros).
2. **Ataque (ATK)**: Corresponde al daño total que posee un personaje. A mayor ataque, mayor será el
   daño que se inflige.
3. **Defensa (DEF)**: Permite mitigar el daño recibido por un enemigo.
4. **Puntos de Vida (HP)**: Son los puntos de vida que posee un personaje. A mayor puntos de vida,
   pasará más tiempo en combate. Los puntos de vida NO pueden ser menores a 0.
5. **Puntos de Ataque (FP)**: Utilizados para realizar ataques. Solo lo poseen Marcos y Luis.

***Consideración 1: El HP inicial al comenzar el juego se contará como el HP máximo, igualmente este último 
podría aumentar al pasar los niveles. Misma lógica para los FP.***

### Tipos de ataques de los jugadores
Cada personaje principal, en su turno podrá ejecutar dos tipos de ataque:
- **Salto**:

  - Corresponde a un ataque normal, de bajo daño, pero que siempre lo recibe el enemigo.
  - Cuesta 1 FP
  - Su constante K para calcular el daño es 1
- **Martillo**:
  - Corresponde a un ataque avanzado, de mayor daño, pero que posee un 25 % de posibilidades de
  fallar
  - Cuesta 2 FP
  - Su constante K para calcular el daño es 1.5

### Cálculo del daño provocado
El daño realizado por un personaje atacante A hacia un personaje atacado B es
> Daño = K · (ATK personaje A) · (LVL personaje A) / (DEF personaje B)

_Donde:_
_K es la constante que entrega el ataque seleccionado.
La regla anterior aplica para todos los daños ocurridos entre enemigos y jugadores._

***Consideración 2: El daño siempre se calculará en función del entero más cercano.***

### Enemigos
Marcos y Luis enfrentarán a enemigos controlados por el computador, estos serán los siguientes:
- Goomba: Es un enemigo genérico. Solo atacarán de forma normal a Marcos y Luis cuando sea su
turno. Podrá ser dañado por cualquier tipo de ataque (Salto, Martillo).
- Spiny: Es un personaje con un caparazón con pinchos. Si un personaje principal ataca a Spiny con un
Salto, entonces el atacante recibirá un daño del 5 % de su HP, y Spiny no recibirá daño. Sin embargo,
Spiny podrá ser dañado por el resto de ataques (Martillo).
- Boo: Es un *fantasmita*. Ataca únicamente a Luis, y si recibe un ataque con martillo, lo esquiva. Puede
recibir el resto de ataques de forma normal (Salto).
Es importante notar que todos los enemigos tendrán un único tipo de ataque para usar contra los personajes
principales, en donde se aplicará la misma fórmula de ataque con K = 0,75.

### Tabla de ataques
La tabla de ataques será descrita a continuación:


|               | **Marcos**| **Luis**  | **Goomba** | **Spiny** | **Boo**  |
| :-------------|:---------:|:---------:|:----------:|:---------:|---------:|
| **Marcos**    |           |           | &#10004;   | &#10004;  | &#10004; |
| **Luis**      |           |           | &#10004;   | &#10004;  |          |
| **Goomba**    | &#10004;  | &#10004;  |            |           |          |
| **Spiny**     | &#10004;  | &#10004;  |            |           |          |
| **Boo**       |           | &#10004;  |            |           |          |

`Cuadro 1: Tabla de ataques. La forma de leer la tabla es: “Nombre de una fila” ataca a “Nombre de una
columna”. Por ejemplo, Marcos ataca a Boo, pero Boo no puede atacar a Marcos.`

***Consideración 3: Cuando un personaje intente atacar a otro sin que esté permitida su interacción,
simplemente no habrá gasto de recursos ni daño infligido.***

### Items
Los personajes principales tendrán la oportunidad de ocupar Objetos, conocidos popularmente como Items.
Los posibles items se resumen a continuación:
1. **Star:**
   - Hace que el personaje que la consuma entre al estado invencible.
2. **Red Mushroom:**
   - Cura al personaje una cantidad de 10 % del HP máximo del personaje.
3. **Honey Syrup:**
   - Restaura al personaje una cantidad de 3 FP.

***Consideración 4: Un personaje principal se dirá "invencible" si no percibe daño de ningún
ataque mientras el efecto este activo.***


## Requisitos aburridos de la Tarea 1
Lo que se pide para la Tarea 1 es:
```
- Crear los personajes principales y los enemigos con sus respectivas estadísticas.
- Crear los Items.
- Que los personajes principales sean capaces de ocupar Items.
- Contemplar las restricciones para los puntos de vida (esto es, que siempre estén entre 0 y HP máximo).
- Crear métodos para utilizar los datos de cada personaje y considerar la privacidad en su código.
- Añadir un método para saber si el personaje está derrotado.
- Si un personaje está K.O. entonces no podrá atacar (siempre ataca con 0 de daño).
```

#### Paso 1: Hacer los primeros tests para jugadores y enemigos.
***Consideración 5: Se crean frases típicas e insultos para jugadores y enemigos, respectivamente,
para darle emoción a los tests.***

_De momento se espera tener una clase **Personaje** que contenga a la clase de **Jugadores** y de **Enemigos**, todas 
abstractas, con el fin de reutilizar funcionalidad común y dar orden y jerarquía al código._
1. Se prueban (en un test de jugadores) los constructores que darán vida a Marcos y Luis (a cada uno le corresponderá 
una clase).
   1. Se imprimen sus "frases típicas" para revisar si ya existen.
   2. Se verifica que no son iguales entre ellos, ni tampoco a un enemigo. 
3. Se prueban sus getters y setters.
   - ***Estos serán usados por muchos tests para hacer pruebas por lo que se necesita que sean públicos 😢.***
   - Las variables de forma interna serán `protected` ó `private`.
   1. Se verifica que la noción de estar K.O. se cumple cuando el personaje está en 0 HP y las restricciones de HP/FP.
   2. Se verifican las restricciones de vida y puntos de ataque (como la vida se implementa para todos los personajes,
   en principio bastaría probar **1.** y **2.** solo con un jugador arbitrario.) 
4. En otro test para enemigos se crean Goomba, Spiny y Boo (a cada uno le corresponderá una clase).
   1. Se imprimen sus "insultos" para revisar si ya existen.
   2. Se prueban sus getters y setters, cada enemigo tendrá su "tipo" que le identifica y guarda su información.
#### Paso 2: Hacer los tests para que jugadores puedan usar items.
***Consideración 6: Dos jugadores son iguales si tienen la misma clase (Ej.: Marcos) y sus estadísticas son las mismas,
pero no dependen los items que posean. Lo mismo para los enemigos, pero contando también su "tipo".***
1. Se prueban los constructores de los items (todos los items pertenecerán a la misma clase de items).
2. Se verifica que no son iguales entre si.
3. Se agregan los items al inventario y se seleccionan para testear su funcionalidad. (La noción de inventario sugiere 
una nueva clase 
**Bolsa de Items** en la cual ir llevando el conteo.)

#### Paso 3: Tests de ataques entre jugadores y enemigos.
1. Se verifican las estadísticas propias de cada ataque (Lo que sugiere una clase **Tipo de ataque** que al menos 
permita guardar la constante de ataque K, la probabilidad de fallar y el costo de puntos de ataque).
2. Se realiza el ataque de Marcos y Luis a Goomba.
   1. Se verifican condiciones "normales" y se agrega un tope máximo alto de FP a los jugadores para saber si gastan 
   correctamente sus puntos de ataque.
   2. Luego se testea el ataque de tipo Martillo, como este tiene probabilidad de fallar, se implementa mediante un 
ciclo `while()` que rescatará la proporción de ataques conectados y fallados, revisando finalmente si su FP se gastó
   correctamente.
   3. Igual que antes, se testea el Salto, a pesar de tener nula probabilidad de fallar, todos los ataques implementarán
   el ataque de manera probabilística, para que el proceso que haga los cálculos sea generalizable a nuevos ataques con
   probabilidad "p" arbitraria de fallar (y también se verifica el correcto gasto de fp).
   4. Luego se repite el experimento con 0 puntos de ataque (pero estando "no K.O.")
   5. Finalmente se repite el experimento estando K.O. pero con puntos de ataque disponibles, para ver que ataca con 
   daño 0.
3. Se realiza el ataque de Marcos y Luis a Spiny.
   1. Igual que antes se verifican las c.i. y se usan ciclos `while()` para medir con probabilidad.
   2. Una salvedad en este caso, es verificar que Spiny hace daño cuando es atacado con salto, el jugador gasta su FP y
   Spiny no recibe daño.
   3. Igual que en el caso anterior se prueban los ataques para K.O. con FP y sin F.P pero no K.O.
4. Marcos ataca a Boo
   1. Se verifican las c.i.
   2. Al igual que antes se hacen ciclos para probar ante probabilidad los ataques.
   3. Se debe considerar ahora el caso en que Boo es atacado con Martillo, ya que aquí Boo no recibe daño, pero sus FP
   igualmente deben ser consumidos, ya que hizo el ataque.
   4. Igual que en el caso anterior se prueban los ataques para K.O. con FP y sin F.P pero no K.O.
5. Luis ataca a Boo
   1. Este caso es fácil de verificar, solo hace falta revisar que Luis no haga daño alguno a Boo, con ningún ataque.
6. Goomba y Spiny atacan a Marcos y Luis
   1. Ahora debemos verificar que si el jugador está invencible los enemigos no generen daño.
   2. También se debe testear que si el enemigo esta K.O. ataque con daño 0.
7. Boo ataca a Marcos
   1. Este caso también es sencillo de verificar, solo basta ver que Boo (estando "no K.O.") no haga daño a Marcos.
8. Boo ataca a Luis
   1. Nuevamente se debe verificar que el jugador no reciba daño si está invencible.
   2. Además de testear que el enemigo ataque con daño 0 cuando esta K.O.

#### Paso 4: Diseñar las interfaces y Enums.
1. Se crean las interfaces de los métodos necesarios que deben poder ejecutar **Personajes**, **Jugadores** y 
**Enemigos**.
   1. En la primera estarán todos los getters y setters comunes, un print para ver el texto y el invariante (abstracto).
   2. En la segunda los getters y setters del FP, los de verificación de invencibilidad y funcionalidad para operar los
   items del inventario (**la clase "Bolsa de Items" no tendrá interface, ya que trabaja bien "sin interactuar" fuera del 
   jugador**), como añadir, seleccionar y obtener el armamento (para visibilidad), la frase típica y
   el invariante, ahora de HP y FP.
   3. En la tercera estará el getter y setter de cada **tipo de enemigo** (creado en una clase enum), su insulto y el invariante, en este caso solo
   de HP.
2. Se crea la interfaz para los **Items**, que permitirá fungir el item en cuestión y obtener el **tipo de item** (creado en
una clase enum).
3. Interfaces para Double Dispatch que deberán implementar las clases del jugador y el enemigo.
   1. Se crea el interface **Ataque del Jugador** y **Ataque del Enemigo**.
   2. Cada una se encargará de un method de ataque al contrincante y métodos que avisan personaje atacado
   quien precisamente los está atacando (Ej.:Jugador implementa **Atacado por Boo**, Enemigo
   implementa **Atacado por Luis**).
   
#### Paso 5: Paso TRIVIAL (¡Programar 🤓!).
1. Básicamente llevar a cabo la implementación de los métodos descritos en las interfaces.
2. Agregar además los métodos `Equals()`, `hashCode()` y `toString()` en las clases que requieran testeo de duplicidad y
constructores mencionados en el apartado de testeo.
3. Programar la **Bolsa de items** con un enum map sobre el enum de los **tipos de item**. para asegurarnos de siempre 
tener en cuenta todos los items que el jugador podría llegar a obtener.

***Consideración 7: Probablemente el double dispatch no está bien implementado, ya que se hizo uso de 
un if para los casos de tipos de ataque que no deben funcionar como lo hacen de costumbre, como Spiny con Salto
y Boo con martillo, por lo que si es necesario, se espera modificar el enum de **tipos de ataque** por una clase normal.***

***Consideración 8: El cálculo de probabilidades se hizo obteniendo un número aleatorio en [0,1] y verificando si era o
no mayor a la probabilidad "p" de fallar. Los lectores podrán dar cuenta que esto entrega `True` con probabilidad "1-p" y 
`False` con probabilidad "p". Lo que puede ser utilizado para dar el veredicto de sí atacar o no.***

### Paso 6: Documentar (Javadoc).
Se documentan todas las clases, interfaces y métodos públicos con la metodología "Java de Google" para los Javadocs.

### Paso 7: Coverage.

Buscar el coverage del 100% EN TODOS LOS CAMPOS (si, en TODOS).

### Paso 7: Resumen.

Hacer este breve resumen (si, breve...).

### Paso 7: Diagrama UML.
![alt text](UML_Diagram_T1_ModelComplete.png)
