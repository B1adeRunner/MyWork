#include "stdio.h"
#include "conio.h"
#include "stdlib.h"
#include "math.h"

					//ЛАБАРТОРНАЯ РАБОТА №3		ВАРИАНТ 7
		//ЗАДАНИЕ: x+((x^3)/3!)+((x^5)/5!)+...((x^2n+1)/2n+1!)
double h1(float e);

int main() {	
	float e=0.0001;
	system("cls");
	h1(e);
	getch();	
}
	
double h1(float e) {
	int n=1;
	float x, S=0, Sn;
	for(x=0.1;x<1.1;x+=0.1) {
		for(Sn=x;Sn>e;n++) {
			S+=Sn;
			Sn*=x*x/(2*n*(2*n+1));
		}
		printf("%f  %f  %f\n", x, S, sinh(x));
		n=1;
		S=0;
	}
}
