#include "stdio.h"
#include "conio.h"
#include "stdlib.h"
#include <windows.h>

#define N 500
#define ESC 27

		//КОНТРОЛЬНАЯ РАБОТА:	ИГРОВАЯ ПРОГРАММА:"ЗМЕЙКА" 				ВАРИАНТ 6
		
		//ЗАДАНИЕ: 				"ЗМЕЙКА". По экрану движется червяк, направление 
		//						движения головы которого можно менять (стрелками).
		//		  			 	Червяк ест призы и растет. Задача - при движении 
		//						головы не наткнуться на хвост и границы экрана.

void gotoxy(int x, int y){											//перемещение курсора в координаты 'x' и 'y'
  COORD scrn;    
  HANDLE hOuput = GetStdHandle(STD_OUTPUT_HANDLE);
  scrn.X = x; scrn.Y = y;
  SetConsoleCursorPosition(hOuput,scrn);
}

void Dvijenie();
void Telo_Risuet(int x[], int y[], int j, int Size);				//поочередно отрисовывает части тела
void Telo_Stiraet(int x[], int y[], int j, int Size);				//поочередно стирает части тела
void Ramka();														//рисует рамку поля игры (48 x 40)
void Proverka_Ramka(int x[], int y[]);								//делает невозможным выход за пределы игрового поля
void Eda(int x[], int y[], int &xe, int &ye, int Size, int xp, int yp);				//рисует 1 еду на игровом поле, если ее там нет
void Lovushka(int x[], int y[], int xe, int ye, int Size, int &xp, int &yp);				//рисует поганку на игровом поле, если голова змеи изменилась на n - z
int Proverka_Trapeza(int x[], int y[], int &xe, int &ye, int &pp, int &Size, int &S, int P, char &ZZ); 	//проверка на поглощение еды
int Proverka_Trapeza_poganka(int x[], int y[], int &xp, int &yp, int &P, int S, int Size, int &pp);	//делает инверсию управления после поедания поганки
int Proverka_Kanibalizm(int j, int Size, int x[], int y[], int &exit);			//проверка на канибализм


int main (){
	Ramka();														//рисует рамку поля игры (48 x 40)
	Dvijenie();														//многократно: рисует, стирает, перемещается
}

void Dvijenie(){
	int code, dx=1, dy=0, x[N]={15}, y[N]={15}, exit=0, Size=2, j, xe=0, ye=0, S=0, xp=0, yp=0, P=0, pp=1;
	char ZZ='x';
	while (!exit){													//пока не нажат ESC делать следующее...	
		for(int i=0;i<1;i++){										//многократный запрос на нажатие клавиши
			if (kbhit()){
				code=getch();
				if (pp==1)
					switch(code){
						case ESC: exit=1;							//при нажатии ESC завершает программу
						case 'w': if (dy!=1) {dx=0; dy=-1;}	break;	//поворачивает вверх, если до этого не двигался вниз		
						case 's': if (dy!=-1) {dx=0; dy=1;}	break;	//поворачивает вниз, если до этого не двигался вверх
						case 'a': if (dx!=1) {dx=-1; dy=0;} break; 	//поворачивает влево, если до этого не двигался вправо
						case 'd': if (dx!=-1) {dx=1; dy=0;} break; 	//поворачивает вправо, если до этого не двигался влево
					}
				else {												//инверсия управления при поедании поганки
					switch(code){
						case ESC: exit=1;							
						case 's': if (dy!=1) {dx=0; dy=-1;}	break;			
						case 'w': if (dy!=-1) {dx=0; dy=1;}	break;	
						case 'd': if (dx!=1) {dx=-1; dy=0;} break; 	
						case 'a': if (dx!=-1) {dx=1; dy=0;} break; 	
					}
				}
			}
		}
		Telo_Risuet(x, y, j, Size);								//поочередно отрисовывает части тела	
		gotoxy(x[0],y[0]);										//переходим в координаты головы змеи
		printf("%c", ZZ);										//печатаем голову змейки
		Eda(x, y, xe, ye, Size, xp, yp);						//рисует 1 еду на игровом поле, если ее там нет
		Proverka_Trapeza(x, y, xe, ye, pp, Size, S, P, ZZ);		//проверка на поглощение еды
		for(int i=110;i<122;i++)								//если голова змеи изменилась на n - z, то появляется поганка
			if (ZZ==i){
				Lovushka(x, y, xe, ye, Size, xp, yp);
			}
		Proverka_Trapeza_poganka(x, y, xp, yp, P, S, Size, pp);	//делает инверсию управления после поедания поганки
		Sleep(90);												//ждем 90 миллисекунд
		gotoxy(x[0],y[0]);										//возвращаемся в координаты головы змеи
		printf(" ");											//стираем голову змейки		
		Telo_Stiraet(x, y, j, Size);							//поочередно стирает части тела
		x[0]+=dx;												//двигаемся в выбранном ранее направлении
		y[0]+=dy;	
		Proverka_Kanibalizm(j, Size, x, y, exit);				//проверка на канибализм
		Proverka_Ramka(x, y);									//делает невозможным выход за пределы игрового поля
	}
}

void Telo_Risuet(int x[], int y[], int j, int Size){				
	for(j=Size;j>=1;j--){
		x[j]=x[j-1];
		y[j]=y[j-1];
	}
	for(j=Size;j>=1;j--){
		gotoxy(x[j],y[j]);
		printf("o");
	}
}			

void Telo_Stiraet(int x[], int y[], int j, int Size){			
	for(j=Size;j>=1;j--){
		gotoxy(x[j],y[j]);
		printf(" ");
	}
}			

void Ramka(){												
	int i, j;
	for(i=0;i<50;i++)											//верхняя горизонтальная граница
		printf("#");
	printf("\n");
	for(i=0;i<40;i++){											//левая и правая вертикальные границы
		printf("#");
		for(j=0;j<48;j++)		
			printf(" ");
		printf("#\n");
	}
	for(i=0;i<50;i++)											//нижняя горизонтальная граница
		printf("#");
}

void Proverka_Ramka(int x[], int y[]){							
	if (x[0]>48)
		x[0]=1;	
	if (x[0]<1)
		x[0]=48;	
	if (y[0]>40)
		y[0]=1;	
	if (y[0]<1)
		y[0]=40;	
}

void Eda(int x[], int y[], int &xe, int &ye, int Size, int xp, int yp){		
	int i, t=Size;
	if ((xe==0) && (ye==0)){
		xe=rand()%46+2;
		ye=rand()%38+2;
		for(i=Size;i>=1;i--)
			if (((xe==x[i]) && (ye==y[i])) || ((xe==x[0]) && (ye==y[0])) || ((xe==xp) && (ye==yp)))
				t=0;
		while (t!=Size) {
			xe=rand()%46+2;
			ye=rand()%38+2;
			for(i=Size;i>=1;i--){
				if (((xe!=x[i]) || (ye!=y[i])) && ((xe!=x[0]) || (ye!=y[0])) && ((xe!=xp) || (ye!=yp)))
					t++;
				else
					t=0;
			}
		}
		gotoxy(xe,ye);
		printf("e");
	}
}

void Lovushka(int x[], int y[], int xe, int ye, int Size, int &xp, int &yp){		
	int i, t=Size;
	if ((xp==0) && (yp==0)){
		xp=rand()%46+2;
		yp=rand()%38+2;
		for(i=Size;i>=1;i--)
			if (((xp==x[i]) && (yp==y[i])) || ((xp==x[0]) && (yp==y[0])) || ((xe==xp) && (ye==yp)))
				t=0;
		while (t!=Size) {
			xp=rand()%46+2;
			yp=rand()%38+2;
			for(i=Size;i>=1;i--){
				if (((xp!=x[i]) || (yp!=y[i])) && ((xp!=x[0]) || (yp!=y[0])) && ((xe!=xp) || (ye!=yp)))
					t++;
				else
					t=0;
			}
		}
		gotoxy(xp,yp);
		printf("@");
	}
}

int Proverka_Trapeza(int x[], int y[], int &xe, int &ye, int &pp, int &Size, int &S, int P, char &ZZ){
	if ((x[0]==xe) && (y[0]==ye)){			
		if(pp==0){										//возвращаем управление в нормальный режим, если была инверсия
			pp=1;
			gotoxy(55,5);
			printf("                 ");
		}
		Size++;											//увеличивает размер змейки, если покушал
		S++;											//счетчик размера змеи
		xe=0;											//обнуляем после трапезы координаты еды
		ye=0;
		gotoxy(52,1);
		printf("Skushal: %d	Razmer zmei: %d	Poganki: %d", S, Size, P);		//выводит на экран информацию по игре
		if (S%10==0){									//каждые набранные 10 единиц еды меняется тип головы
			ZZ=rand()%25+97;							//латиница прописная в ASCII 97 - 122 = 25
		}
	}
}
int Proverka_Trapeza_poganka(int x[], int y[], int &xp, int &yp, int &P, int S, int Size, int &pp){	
	if ((x[0]==xp) && (y[0]==yp)){				
		xp=0;											//обнуляем после поедания поганки ее координаты
		yp=0;
		gotoxy(55,5);
		printf("GOLOVOKRUJENIE!!!");
		P++;
		gotoxy(52,1);
		printf("Skushal: %d	Razmer zmei: %d	Poganki: %d", S, Size, P);
		pp=0;											//маяк управления
	}
}

int Proverka_Kanibalizm(int j, int Size, int x[], int y[], int &exit){		
	for(j=Size;j>=1;j--){									
		if ((x[0]==x[j]) && (y[0]==y[j])){				//если координаты головы змеи равны координатам части тела змеи, то выход
			exit=1;											
		}
	}
}
	
