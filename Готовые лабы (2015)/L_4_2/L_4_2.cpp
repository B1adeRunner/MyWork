#include "stdio.h"
#include "conio.h"
#include "math.h"
#include "stdlib.h"

#define N 500

					//ЛАБАРТОРНАЯ РАБОТА №4		ВАРИАНТ 2
		//ЗАДАНИЕ: В СТРОКЕ, СОДЕРЖАЩЕЙ ПОСЛЕДОВАТЕЛЬНОСТЬ СЛОВ, НАЙТИ КОНЕЦ ПРЕДЛОЖЕНИЯ,
		//		   ОБОЗНАЧЕННЫЙ СИМВОЛОМ "ТОЧКА". В СЛЕДУЩЕМ СЛОВЕ ПЕРВУЮ СТРОЧНУЮ БУКВУ 
		//		   ЗАМЕНИТЬ НА ПРОПИСНУЮ (ЗАГЛАВНУЮ)
		
		
int main() {
	char FourLab();		//объявление функции
	FourLab();			//вызов функции
	getch();						
}

int FourLab() {
	int i, k, n;	
	char A[N]="London is the capital of Gre .at Brit4ain.   westminster is. 1the governmental part of London.\n";
	printf("%s\n", A);
	for (i=0;i<N;i++) {								//перебор символов, пока не найдет символ точки
		if (A[i]==46) {
			i++;									//переход к следующему за точкой символу
			for(;i<N;i++) {							//задает перебор символов после точки 
				for(k=97,n=0;k<123;k++){			//проверяет i элемент на равенство строчным
					if ((A[i]==k || A[i]==(65+n)) && ((A[i-1]==' ') || (A[i-1]=='.'))) {
						printf("%c ---> ", A[i]);	
						A[i]=65+n;					//заменяет на заглавную букву (A = 65 (ASCII))
						printf("%c\n\n", A[i]);
						goto ZZ;
					}
						n++;
				}
			}
		} 
	ZZ:;	
	}
	printf("%s", A);
}


