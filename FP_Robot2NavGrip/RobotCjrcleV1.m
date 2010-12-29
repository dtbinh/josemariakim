y = 300;
x = 250;
a = -45;
%a = -135;
ar = abs(a) * pi/180;
r = 20;

if (a >= 0) && (a < 90)
   yr = y + r*sin(ar);
   xr = x - r*(1 + cos(ar));
end
if (a >= 90) && (a < 180)
   yr = y + r*sin(pi - ar);
   xr = x - r*(1 - cos(pi - ar));
end
if (a >= -90) && (a < 0)
   yr = y - r*sin(ar);
   xr = x - r*(1+cos(ar));
end
if (a >= -180) && (a < -90)
    yr = y - r*sin(pi - ar);
    xr = x - r*(1 - cos(pi - ar));
end

disp(['x,y ' num2str(x) ',' num2str(y) ' x´,y´ '  num2str(xr) ',' num2str(yr)]);

xa = [x, xr];
ya = [y, yr];

figure(1);
plot(y, x, '+b');
hold all
plot(yr, xr, '+r');
xlim([200 340]);
ylim([200 340]);

