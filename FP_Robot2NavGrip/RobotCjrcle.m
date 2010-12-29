%y = 300;
%x = 250;
%a = -45;
%a = -135;

x = 702;
y = abs(-156);
%head = -171;
%head = -180;
%head = 45;
%head = -45;
%head = 135;
head = -135;
r = 20;

%if (a >= 0) && (a < 90) % A
if (head >= -180) && (head < -90)
   a = 180 - abs(head); 
   ar = abs(a) * pi/180;
   yr = y + r*sin(ar);
   xr = x - r*(1 + cos(ar));
end

%if (a >= 90) && (a < 180) % B
if (head >= -90) && (head < 0)
   a = abs(head);
   ar = abs(a) * pi/180;   
   yr = y + r*sin(ar);
   xr = x - r*(1 - cos(ar));
end

%if (a >= -90) && (a < 0) % C
if (head > 90) && (head <= 180)
   a = 180 - abs(head);
   ar = abs(a) * pi/180;   
   yr = y - r*sin(ar);
   xr = x - r*(1 + cos(ar));
end

%if (a >= -180) && (a < -90) % D
if (head >= 0) && (head <= 90)
   a = abs(head);
   ar = abs(a) * pi/180;   
   yr = y - r*sin(ar); 
   xr = x - r*(1 - cos(ar));
end

disp(['x,y ' num2str(x) ',' num2str(y) ' x´,y´ '  num2str(xr) ',' num2str(yr)]);

xa = [x, xr];
ya = [y, yr];

figure(1);
plot(y, x, '+b');
hold all
plot(yr, xr, '+r');
ylim([650 750]);
xlim([100 200]);

