#include "stdio.h"
#include "conio.h"
#include "math.h"
#include "stdlib.h"

#define N 500

					//����������� ������ �4		������� 2
		//�������: � ������, ���������� ������������������ ����, ����� ����� �����������,
		//		   ������������ �������� "�����". � �������� ����� ������ �������� ����� 
		//		   �������� �� ��������� (���������)
		
		
int main() {
	char FourLab();		//���������� �������
	FourLab();			//����� �������
	getch();						
}

int FourLab() {
	int i, k, n;	
	char A[N]="London is the capital of Gre .at Brit4ain.   westminster is. 1the governmental part of London.\n";
	printf("%s\n", A);
	for (i=0;i<N;i++) {								//������� ��������, ���� �� ������ ������ �����
		if (A[i]==46) {
			i++;									//������� � ���������� �� ������ �������
			for(;i<N;i++) {							//������ ������� �������� ����� ����� 
				for(k=97,n=0;k<123;k++){			//��������� i ������� �� ��������� ��������
					if ((A[i]==k || A[i]==(65+n)) && ((A[i-1]==' ') || (A[i-1]=='.'))) {
						printf("%c ---> ", A[i]);	
						A[i]=65+n;					//�������� �� ��������� ����� (A = 65 (ASCII))
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


