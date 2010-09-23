load 'oneClapOK.txt'
load 'oneClap2OK.txt'
x=[1:1:size(oneClapOK)]; 
figure(1)
plot(x,oneClapOK,'r')
xlabel('Sample N')
ylabel('Sensor reading')
title('One Clap 1')
figure(2)
y=[1:1:size(oneClap2OK)]; 
plot(y,oneClap2OK)
xlabel('Sample N')
ylabel('Sensor reading')
title('One Clap 2')
