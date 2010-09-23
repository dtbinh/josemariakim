//============================================================================
// Name        : Convert.cpp
// Author      : Kim Bjerge
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <stdio.h>
#include <string.h>
#include <iostream>
using namespace std;

int main(int argc, char *argv[]) {
	FILE *fp_in;
	FILE *fp_out;
	char name[50];
	int tmp_val;

	if (argc > 1) {
		strcpy(name, argv[1]);
	}
	else
	{
		strcpy(name, "Sample.txt");
	}

	fp_in = fopen(name, "r");
	fp_out = fopen("out.txt", "w");

	if (fp_in == 0)
	{
		cout << "Could not open " << name << endl;
		return 1;
	}

	while (fscanf(fp_in, "%d,", &tmp_val) != EOF)
	{
		fprintf(fp_out, "%d,\n", tmp_val);
	};

	cout << "End of file " << name << endl;
	fclose(fp_in);
	fclose(fp_out);

	return 0;
}
