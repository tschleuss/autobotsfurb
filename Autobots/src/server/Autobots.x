struct mapConfig {
	string caminho<>;
	int linhas;
	int colunas;
};

struct botPosition{
	int x;
	int y;
};

struct mapLayoutPercent{
	int waterPercent;
	int grassPercent;
	int treesPercent;
};

program Autobots {
	version Autobots_v1 {
		mapConfig getMap(void)=1;
		botPosition getBotPosition(void)=2;
		mapLayoutPercent getLayout(void)=3;
	}=1;
}=4;