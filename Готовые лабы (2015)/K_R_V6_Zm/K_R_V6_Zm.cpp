#include "stdio.h"
#include "conio.h"
#include "stdlib.h"
#include <windows.h>

#define N 500
#define ESC 27

		//����������� ������:	������� ���������:"������" 				������� 6
		
		//�������: 				"������". �� ������ �������� ������, ����������� 
		//						�������� ������ �������� ����� ������ (���������).
		//		  			 	������ ��� ����� � ������. ������ - ��� �������� 
		//						������ �� ���������� �� ����� � ������� ������.

void gotoxy(int x, int y){											//����������� ������� � ���������� 'x' � 'y'
  COORD scrn;    
  HANDLE hOuput = GetStdHandle(STD_OUTPUT_HANDLE);
  scrn.X = x; scrn.Y = y;
  SetConsoleCursorPosition(hOuput,scrn);
}

void Dvijenie();
void Telo_Risuet(int x[], int y[], int j, int Size);				//���������� ������������ ����� ����
void Telo_Stiraet(int x[], int y[], int j, int Size);				//���������� ������� ����� ����
void Ramka();														//������ ����� ���� ���� (48 x 40)
void Proverka_Ramka(int x[], int y[]);								//������ ����������� ����� �� ������� �������� ����
void Eda(int x[], int y[], int &xe, int &ye, int Size, int xp, int yp);				//������ 1 ��� �� ������� ����, ���� �� ��� ���
void Lovushka(int x[], int y[], int xe, int ye, int Size, int &xp, int &yp);				//������ ������� �� ������� ����, ���� ������ ���� ���������� �� n - z
int Proverka_Trapeza(int x[], int y[], int &xe, int &ye, int &pp, int &Size, int &S, int P, char &ZZ); 	//�������� �� ���������� ���
int Proverka_Trapeza_poganka(int x[], int y[], int &xp, int &yp, int &P, int S, int Size, int &pp);	//������ �������� ���������� ����� �������� �������
int Proverka_Kanibalizm(int j, int Size, int x[], int y[], int &exit);			//�������� �� ����������


int main (){
	Ramka();														//������ ����� ���� ���� (48 x 40)
	Dvijenie();														//�����������: ������, �������, ������������
}

void Dvijenie(){
	int code, dx=1, dy=0, x[N]={15}, y[N]={15}, exit=0, Size=2, j, xe=0, ye=0, S=0, xp=0, yp=0, P=0, pp=1;
	char ZZ='x';
	while (!exit){													//���� �� ����� ESC ������ ���������...	
		for(int i=0;i<1;i++){										//������������ ������ �� ������� �������
			if (kbhit()){
				code=getch();
				if (pp==1)
					switch(code){
						case ESC: exit=1;							//��� ������� ESC ��������� ���������
						case 'w': if (dy!=1) {dx=0; dy=-1;}	break;	//������������ �����, ���� �� ����� �� �������� ����		
						case 's': if (dy!=-1) {dx=0; dy=1;}	break;	//������������ ����, ���� �� ����� �� �������� �����
						case 'a': if (dx!=1) {dx=-1; dy=0;} break; 	//������������ �����, ���� �� ����� �� �������� ������
						case 'd': if (dx!=-1) {dx=1; dy=0;} break; 	//������������ ������, ���� �� ����� �� �������� �����
					}
				else {												//�������� ���������� ��� �������� �������
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
		Telo_Risuet(x, y, j, Size);								//���������� ������������ ����� ����	
		gotoxy(x[0],y[0]);										//��������� � ���������� ������ ����
		printf("%c", ZZ);										//�������� ������ ������
		Eda(x, y, xe, ye, Size, xp, yp);						//������ 1 ��� �� ������� ����, ���� �� ��� ���
		Proverka_Trapeza(x, y, xe, ye, pp, Size, S, P, ZZ);		//�������� �� ���������� ���
		for(int i=110;i<122;i++)								//���� ������ ���� ���������� �� n - z, �� ���������� �������
			if (ZZ==i){
				Lovushka(x, y, xe, ye, Size, xp, yp);
			}
		Proverka_Trapeza_poganka(x, y, xp, yp, P, S, Size, pp);	//������ �������� ���������� ����� �������� �������
		Sleep(90);												//���� 90 �����������
		gotoxy(x[0],y[0]);										//������������ � ���������� ������ ����
		printf(" ");											//������� ������ ������		
		Telo_Stiraet(x, y, j, Size);							//���������� ������� ����� ����
		x[0]+=dx;												//��������� � ��������� ����� �����������
		y[0]+=dy;	
		Proverka_Kanibalizm(j, Size, x, y, exit);				//�������� �� ����������
		Proverka_Ramka(x, y);									//������ ����������� ����� �� ������� �������� ����
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
	for(i=0;i<50;i++)											//������� �������������� �������
		printf("#");
	printf("\n");
	for(i=0;i<40;i++){											//����� � ������ ������������ �������
		printf("#");
		for(j=0;j<48;j++)		
			printf(" ");
		printf("#\n");
	}
	for(i=0;i<50;i++)											//������ �������������� �������
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
		if(pp==0){										//���������� ���������� � ���������� �����, ���� ���� ��������
			pp=1;
			gotoxy(55,5);
			printf("                 ");
		}
		Size++;											//����������� ������ ������, ���� �������
		S++;											//������� ������� ����
		xe=0;											//�������� ����� ������� ���������� ���
		ye=0;
		gotoxy(52,1);
		printf("Skushal: %d	Razmer zmei: %d	Poganki: %d", S, Size, P);		//������� �� ����� ���������� �� ����
		if (S%10==0){									//������ ��������� 10 ������ ��� �������� ��� ������
			ZZ=rand()%25+97;							//�������� ��������� � ASCII 97 - 122 = 25
		}
	}
}
int Proverka_Trapeza_poganka(int x[], int y[], int &xp, int &yp, int &P, int S, int Size, int &pp){	
	if ((x[0]==xp) && (y[0]==yp)){				
		xp=0;											//�������� ����� �������� ������� �� ����������
		yp=0;
		gotoxy(55,5);
		printf("GOLOVOKRUJENIE!!!");
		P++;
		gotoxy(52,1);
		printf("Skushal: %d	Razmer zmei: %d	Poganki: %d", S, Size, P);
		pp=0;											//���� ����������
	}
}

int Proverka_Kanibalizm(int j, int Size, int x[], int y[], int &exit){		
	for(j=Size;j>=1;j--){									
		if ((x[0]==x[j]) && (y[0]==y[j])){				//���� ���������� ������ ���� ����� ����������� ����� ���� ����, �� �����
			exit=1;											
		}
	}
}
	
