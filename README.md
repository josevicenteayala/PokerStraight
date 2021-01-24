# Test Dafiti

## Resumen

PockerStraight permite determinar su una lista de números que equivale a un conjunto de cartas de Poker, se encuentra ordenda como escalera.
La escalera es una lista ordenada de números consecutivos, donde los valores posibles por carta son los números del 2 al 14 y la escalera
puede ser de un máximo de 7 números y mínimo de 5. El número 14 hace las veces de 1 y 14, por ende puede ir en la primera posición de la lista
o al final. Si la lista contiene el número 2 y el 14, este asume el valor de la primera posición, ejemplo: 14,2,3,4,5. Si el 2 no se está presente pero si el 14,
su valor ordinal no cambia, ejemplo: 9,10,11,12,13,14.

## Autor

* **José Vicente Ayala Luna** - *Presentación prueba de ingreso* - https://github.com/vin001gmailcom/PokerStraight.git

## Generalidades

* Las cartas siempre tienen valores entre 2 y 14, donde 14 es el AS.
* El AS tambien cuenta como 1.
* La cantidad de cartas puede variar, pero nunca es superior a 7 ni inferior a 5.


## Descripción del programa

La aplicación está compuesta de la función para determinar si existe una escalera válida en una lista de números y la exposición de una imagen via GET para validar la parte de la prueba relacionada con https.

### Estructura de paquetes
* com.dafiti.poker: contrato funcional para definir lo necesario para determinar la escalera de poker.
* com.dafiti.poker.controllers: implementación del contrato funcional para realizar la exposición de una imagen vía navegador.
* com.dafiti.poker.utils: paquete que contiene utilidades importantes para la solución incluyendo la que valida la escalera de poker.

### Clases relacionadas con la escalera de poker

```
PokerRules: contiene las reglas que facilitan la validación de la escalera.
StraightPoker: contine la función para realizar la validación de la escalera al igual que utilitarios para realizar ordenamiento, control y validación de reglas.
StraigthPokerException: esquema de excepción cuando la entrada o validación de negocio no cumple.
```

### Patrones y estrategias

La implementación utiliza la versatilidad que brinda el mecanismo de comparación con Comparator y es así como se aplicó el ordenamiento de la escalera.

### Principios aplicados

```
Responsabilidad única para las diversas clases.
Centralización de responsabilidades.
```

### Pre-requisitos

#### Se debe realizar la clonación del repositorio o descargar los fuentes desde este gestor de configuración https://github.com/vin001gmailcom/PokerStraight.git, para ello se debe usar un cliente GIT de preferencia.
#### IDE: Para el desarrollo de la prueba se utilizó Spring Tool Suite Versión 4 pero se puede utilizar el IDE de preferencia.

```
Algunas herramientas de GIT son SourceTree, TortoiseGit, GitBash.
Algunos IDEs son Eclipse, Netbeans, Jdeveloper, IntelliJ.
```

### Instalación

#### Importar el proyecto al IDE

```
Se debe importar los fuentes descargados al IDE de preferencia como proyecto gradle.
```

Una vez importado el proyecto al IDE, se deben refrescar dependencias usando comandos de gradle, en Eclipse la gestión de dependencias se realiza al momento de importar el proyecto.
Se debe configurar la máquina virtual de Java (1.8 recomendada) y adicionar la librería de Junit versión 5.

## Ejecución

La clase que contine la función de validación de la escalera de poker es **com.dafiti.poker.utils.StraightPoker** mediante el método **isStraightPoker** el cual tiene el control de excepción y retorna un booleano, donde true es escalera váilda y false es lo contrario.
Es posible realizar la misma validación a través del método de la misma clase **verifyStraightPoker** pero en este caso se debe manejar la excepción para los casos de fallo.

### Ejecutar programa

La clase **com.dafiti.poker.utils.StraightPokerTest** tiene un método **main** el cual se puede ejecutar mediante las opciones del IDE o por invocación vía comandos de java.
Al ejecutarlo, se realiza la validación de la siguiente entrada **[14, 10, 9, 11, 13, 12, 2]**  cuya respuesta es "[14, 10, 9, 11, 13, 12, 2] is a valid straight poker line: true". Esposible cambiar la entrada para validar una lista diferente.
Existen igualmente un conjunto de pruebas unitarias **com.dafiti.poker.utils.StraightPokerSpec** que realizan diferentes escenarios posibles para la validación de la escalera.


```
public class StraightPoker {

	public static final String INVALID_CARD_LIST_ONLY_5_7_ALLOWED = "Invalid card list, only size beteween 5-7 allowed";
	
	
	/**
	 * This is a wrapper method to check if a list of cards is a valid straight in poker game. 
	 * It handles StraightPokerException when validations fails. Is useful if caller does not handle Exceptions.
	 * @param cards to validate
	 * @return boolean, true if the list is valid or false if It does not.
	 */
	public boolean isStraightPoker(List<Integer> cards) {
		boolean isStraight = true;
		try {
			return verifyStraightPoker(cards);
		} catch (StraightPokerException e) {
			//TODO for version2: Log this exception
			isStraight = false;
		}
		return isStraight;
	}
	
	/**
	 * This method check if a list of cards is a valid straight in poker game. It throws StraightPokerException when validations fails.
	 * @param cards to validate
	 * @return boolean, true if the list is valid or false if It does not.
	 * @throws StraightPokerException
	 */
	public boolean verifyStraightPoker(List<Integer> cards) throws StraightPokerException {
		// Validation to control Integer values
		List<Integer> cardListCorrectValues = validateCardsValues(cards);

		// Validation to control card size
		if (cardListCorrectValues.isEmpty() || cardListCorrectValues.size() < PokerRules.NUMBER_OF_CARDS_MIN_ALLOWED
				|| cardListCorrectValues.size() > PokerRules.NUMBER_OF_CARDS_MAX_ALLOWED)
			throw new StraightPokerException(INVALID_CARD_LIST_ONLY_5_7_ALLOWED);

		// Sort the List
		sortList(cardListCorrectValues);
		
		Iterator<Integer> iterator = cardListCorrectValues.iterator();
		boolean isStraight = true;
		Integer previousCard = iterator.next();
		while(iterator.hasNext() && isStraight) {
			Integer nextCard = iterator.next();
			if(previousCard.equals(14)) {
				if(!nextCard.equals(2)) isStraight = false;
			} else if(nextCard.compareTo(previousCard) <= 0) isStraight = false;
			previousCard = nextCard;
		}
		
		return isStraight;
	}

	/**
	 * Sort a list with specific straight poker rules
	 * @param cardListCorrectValues
	 */
	public static void sortList(List<Integer> cardListCorrectValues) {
		if(cardListCorrectValues.contains(2)) {
			Collections.sort(cardListCorrectValues, pokerComparator());
		}else {
			Collections.sort(cardListCorrectValues);
		}
	}

	/** Apply size rules for card list. The valid size is between 5 and 7 cards.
	 * @param cards
	 * @return
	 * @throws StraightPokerException
	 */
	public static List<Integer> validateCardsValues(List<Integer> cards) throws StraightPokerException {
		if (Objects.isNull(cards) || cards.isEmpty())
			throw new StraightPokerException(INVALID_CARD_LIST_ONLY_5_7_ALLOWED);
		return cards.stream().filter(number -> number <= 14 && number >= 2).collect(Collectors.toList());
	}

	/**
	 * Compares its two integers for order. Returns a negative integer, zero, or a
	 * positive integer as numberToCompare is less than, equal to, or greater than
	 * numberRefernce, but there is a special rule when number 14 is present, in those cases
	 * Its behaviour is as number one does.
	 * <p>
	 * 
	 * @return comparator
	 */
	private static final Comparator<? super Integer> pokerComparator() {
		Comparator<? super Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer numberToCompare, Integer numberReference) {
				if (numberReference == 14) {
					return numberToCompare.compareTo(1);
				}
				
				if (numberToCompare == 14) {
					Integer one = 1;
					return one.compareTo(numberReference);
				}				
				return numberToCompare.compareTo(numberReference);
			}
		};
		return comparator;
	}

}

```


### Ejecutar los test

Para la ejecución de los test es posible realizarlo a través del mismo IDE Eclipse mediante clic derecho al proyecto **/run as / JUnit test**. También se puede ejecutar la clase  **com.dafiti.poker.utils.StraightPokerSpec** como Junit Test.
La clase ejecuta 19 pruebas las cuales se encuentran en estado positivo (color verde) para los test.

#### Generalidades de los test

Los test elaborados para la implementación de la solución, permiten verificar diferentes aspectos del proceso de determinar una escalera válida.

```
Este es un ejemplo del test para validar si la lista tiene números repetidos.

	@Test
	public void testIsNotStraightCardsWithRepeatedNumbers() {
		List<Integer> cards = Arrays.asList(7, 7, 12, 11, 3, 4, 14);
		try {
			assertFalse(straightPoker.verifyStraightPoker(cards));
		} catch (StraightPokerException e) {
			assertFalse(e.getMessage().contains(StraightPoker.INVALID_CARD_LIST_ONLY_5_7_ALLOWED));
		}
	}
```

## Despliegue

No es necesario desplegar la solución en ningún ambiente. Para la ejecución solo se requiere una máquina virtual de java ideal 1.8.* Versiones inferiores o superiores no fueron validadas y es posible que se requiera manejo de librerías como mockito y Junit particulares.
## Repositorio

https://github.com/vin001gmailcom/PokerStraight.git