# Test Dafiti

## Resumen

PockerStraight permite determinar su una lista de n�meros que equivale a un conjunto de cartas de Poker, se encuentra ordenda como escalera.
La escalera es una lista ordenada de n�meros consecutivos, donde los valores posibles por carta son los n�meros del 2 al 14 y la escalera
puede ser de un m�ximo de 7 n�meros y m�nimo de 5. El n�mero 14 hace las veces de 1 y 14, por ende puede ir en la primera posici�n de la lista
o al final. Si la lista contiene el n�mero 2 y el 14, este asume el valor de la primera posici�n, ejemplo: 14,2,3,4,5. Si el 2 no se est� presente pero si el 14,
su valor ordinal no cambia, ejemplo: 9,10,11,12,13,14.

## Autor

* **Jos� Vicente Ayala Luna** - *Presentaci�n prueba de ingreso* - https://github.com/vin001gmailcom/PokerStraight.git

## Generalidades

* Las cartas siempre tienen valores entre 2 y 14, donde 14 es el AS.
* El AS tambien cuenta como 1.
* La cantidad de cartas puede variar, pero nunca es superior a 7 ni inferior a 5.


## Descripci�n del programa

La aplicaci�n est� compuesta de la funci�n para determinar si existe una escalera v�lida en una lista de n�meros y la exposici�n de una imagen via GET para validar la parte de la prueba relacionada con https.

### Estructura de paquetes
* com.dafiti.poker: contrato funcional para definir lo necesario para determinar la escalera de poker.
* com.dafiti.poker.controllers: implementaci�n del contrato funcional para realizar la exposici�n de una imagen v�a navegador.
* com.dafiti.poker.utils: paquete que contiene utilidades importantes para la soluci�n incluyendo la que valida la escalera de poker.

### Clases relacionadas con la escalera de poker

```
PokerRules: contiene las reglas que facilitan la validaci�n de la escalera.
StraightPoker: contine la funci�n para realizar la validaci�n de la escalera al igual que utilitarios para realizar ordenamiento, control y validaci�n de reglas.
StraigthPokerException: esquema de excepci�n cuando la entrada o validaci�n de negocio no cumple.
```

### Patrones y estrategias

La implementaci�n utiliza la versatilidad que brinda el mecanismo de comparaci�n con Comparator y es as� como se aplic� el ordenamiento de la escalera.

### Principios aplicados

```
Responsabilidad �nica para las diversas clases.
Centralizaci�n de responsabilidades.
```

### Pre-requisitos

#### Se debe realizar la clonaci�n del repositorio o descargar los fuentes desde este gestor de configuraci�n https://github.com/vin001gmailcom/PokerStraight.git, para ello se debe usar un cliente GIT de preferencia.
#### IDE: Para el desarrollo de la prueba se utiliz� Spring Tool Suite Versi�n 4 pero se puede utilizar el IDE de preferencia.

```
Algunas herramientas de GIT son SourceTree, TortoiseGit, GitBash.
Algunos IDEs son Eclipse, Netbeans, Jdeveloper, IntelliJ.
```

### Instalaci�n

#### Importar el proyecto al IDE

```
Se debe importar los fuentes descargados al IDE de preferencia como proyecto gradle.
```

Una vez importado el proyecto al IDE, se deben refrescar dependencias usando comandos de gradle, en Eclipse la gesti�n de dependencias se realiza al momento de importar el proyecto.
Se debe configurar la m�quina virtual de Java (1.8 recomendada) y adicionar la librer�a de Junit versi�n 5.

## Ejecuci�n

La clase que contine la funci�n de validaci�n de la escalera de poker es **com.dafiti.poker.utils.StraightPoker** mediante el m�todo **isStraightPoker** el cual tiene el control de excepci�n y retorna un booleano, donde true es escalera v�ilda y false es lo contrario.
Es posible realizar la misma validaci�n a trav�s del m�todo de la misma clase **verifyStraightPoker** pero en este caso se debe manejar la excepci�n para los casos de fallo.

### Ejecutar programa

La clase **com.dafiti.poker.utils.StraightPokerTest** tiene un m�todo **main** el cual se puede ejecutar mediante las opciones del IDE o por invocaci�n v�a comandos de java.
Al ejecutarlo, se realiza la validaci�n de la siguiente entrada **[14, 10, 9, 11, 13, 12, 2]**  cuya respuesta es "[14, 10, 9, 11, 13, 12, 2] is a valid straight poker line: true". Esposible cambiar la entrada para validar una lista diferente.
Existen igualmente un conjunto de pruebas unitarias **com.dafiti.poker.utils.StraightPokerSpec** que realizan diferentes escenarios posibles para la validaci�n de la escalera.


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

Para la ejecuci�n de los test es posible realizarlo a trav�s del mismo IDE Eclipse mediante clic derecho al proyecto **/run as / JUnit test**. Tambi�n se puede ejecutar la clase  **com.dafiti.poker.utils.StraightPokerSpec** como Junit Test.
La clase ejecuta 19 pruebas las cuales se encuentran en estado positivo (color verde) para los test.

#### Generalidades de los test

Los test elaborados para la implementaci�n de la soluci�n, permiten verificar diferentes aspectos del proceso de determinar una escalera v�lida.

```
Este es un ejemplo del test para validar si la lista tiene n�meros repetidos.

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

No es necesario desplegar la soluci�n en ning�n ambiente. Para la ejecuci�n solo se requiere una m�quina virtual de java ideal 1.8.* Versiones inferiores o superiores no fueron validadas y es posible que se requiera manejo de librer�as como mockito y Junit particulares.
## Repositorio

https://github.com/vin001gmailcom/PokerStraight.git