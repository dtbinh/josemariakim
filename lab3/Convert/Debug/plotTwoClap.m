load 'twoClapOK.txt'
load 'twoClap2OK.txt'
x=[1:1:size(twoClapOK)]; 
figure(3)
plot(x,twoClapOK,'r')
xlabel('Sample N')
ylabel('Sensor reading')
title('Two Clap 1')
figure(4)
y=[1:1:size(twoClap2OK)]; 
plot(y,twoClap2OK)
xlabel('Sample N')
ylabel('Sensor reading')
title('Two Clap 2')
