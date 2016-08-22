package dev.nuno.updown;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameBoardActivity extends FragmentActivity {
    Baralho a = new Baralho();
    Mesa b = new Mesa(5);
    int posicao;
    TextView[] x;
    TextView[] x1;
    TextView aviso;
    TextView resize_texview;
    Button[] botoesdecima;
    Button[] botoesdebaixo;
    Animation anim_aviso;
    Animation anim_dir;
    Animation anim_esq;
    Animation resize;
    Animation resize1;
    Animation resize2;
    Animation resize3;
    Animation resize4;
    Animation resize5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        a = new Baralho();
        a.criar();
        b = new Mesa(5);
        b.inicio();

        x = new TextView[]{(TextView) findViewById(R.id.carta1),
                                        (TextView) findViewById(R.id.carta2),
                                        (TextView) findViewById(R.id.carta3),
                                        (TextView) findViewById(R.id.carta4),
                                        (TextView) findViewById(R.id.carta5)};

        x1 = new TextView[]{(TextView) findViewById(R.id.carta6),
                                        (TextView) findViewById(R.id.carta7),
                                        (TextView) findViewById(R.id.carta8),
                                        (TextView) findViewById(R.id.carta9),
                                        (TextView) findViewById(R.id.carta10)};

        botoesdecima = new Button[]{(Button) findViewById(R.id.botao1),
                                        (Button) findViewById(R.id.botao2),
                                        (Button) findViewById(R.id.botao3),
                                        (Button) findViewById(R.id.botao4),
                                        (Button) findViewById(R.id.botao5)};

        botoesdebaixo = new Button[]{(Button) findViewById(R.id.botao6),
                                        (Button) findViewById(R.id.botao7),
                                        (Button) findViewById(R.id.botao8),
                                        (Button) findViewById(R.id.botao9),
                                        (Button) findViewById(R.id.botao10)};

        botoesdecima[0].setOnTouchListener(cimaesq);
        botoesdecima[4].setOnTouchListener(cimadir);
        botoesdebaixo[0].setOnTouchListener(baixoesq);
        botoesdebaixo[4].setOnTouchListener(baixodir);

        for(int i = 0; i < 5; i++) {
            x[i].setText(Html.fromHtml(printCarta(b.Linha1[i])));
        }

        aviso = (TextView) findViewById(R.id.aviso);

        anim_dir = AnimationUtils.loadAnimation(this, R.anim.mover_direita);
        anim_esq = AnimationUtils.loadAnimation(this, R.anim.mover_esquerda);
        anim_aviso = AnimationUtils.loadAnimation(this, R.anim.aviso);

        resize1 = AnimationUtils.loadAnimation(this, R.anim.resize1);
        resize2 = AnimationUtils.loadAnimation(this, R.anim.resize2);
        resize3 = AnimationUtils.loadAnimation(this, R.anim.resize3);
        resize4 = AnimationUtils.loadAnimation(this, R.anim.resize4);
        resize5 = AnimationUtils.loadAnimation(this, R.anim.resize5);

        resize_texview = (TextView) findViewById(R.id.resize_texview);

        Toast.makeText(getApplicationContext(), "Jogo iniciado!!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
        OnTouchListener cimaesq = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (b.getPosicao() == 0) {
                        botoesdebaixo[4].setVisibility(View.INVISIBLE);
                        botoesdecima[4].setVisibility(View.INVISIBLE);
                    }
                    posicao = b.getPosicao();
                    Return2Valores ret = b.cima(b.getPosicao());
                    x1[posicao].setText(Html.fromHtml(printCarta(b.Linha2[posicao])));

                    anim_cartas();

                    resize.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            resize_texview.setText(x1[posicao].getText());
                            resize_texview.setVisibility(View.VISIBLE);
                            x1[posicao].setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            resize_texview.setText("");
                            resize_texview.setVisibility(View.INVISIBLE);
                            x1[posicao].setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    resize_texview.startAnimation(resize);

                    if (!ret.getCerto() || b.checkwin(ret.getCerto())) {
                        mostrar_btn_continuar();

                        aviso.setText(ret.getAviso());
                        return false;
                    }

                    anim_dir.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            botoesdecima[posicao].setClickable(false);
                            botoesdebaixo[posicao].setClickable(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            botoesdecima[posicao + 1].setVisibility(View.VISIBLE);
                            botoesdecima[posicao].setVisibility(View.INVISIBLE);
                            botoesdecima[posicao + 1].setOnTouchListener(cimaesq);

                            botoesdebaixo[posicao + 1].setText("Baixo");
                            botoesdebaixo[posicao + 1].setVisibility(View.VISIBLE);
                            botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                            botoesdebaixo[posicao + 1].setOnTouchListener(baixoesq);

                            botoesdecima[posicao].setClickable(true);
                            botoesdebaixo[posicao].setClickable(true);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    botoesdecima[posicao].startAnimation(anim_dir);
                    botoesdebaixo[posicao].startAnimation(anim_dir);

                return true;
            }
            return false;
        }
    };

    OnTouchListener baixoesq = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (b.getPosicao() == 0) {
                    botoesdecima[4].setVisibility(View.INVISIBLE);
                    botoesdebaixo[4].setVisibility(View.INVISIBLE);
                }
                posicao = b.getPosicao();
                Return2Valores ret = b.baixo(posicao);
                x1[posicao].setText(Html.fromHtml(printCarta(b.Linha2[posicao])));

                anim_cartas();

                resize.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        resize_texview.setText(x1[posicao].getText());
                        resize_texview.setVisibility(View.VISIBLE);
                        x1[posicao].setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        resize_texview.setText("");
                        resize_texview.setVisibility(View.INVISIBLE);
                        x1[posicao].setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                resize_texview.startAnimation(resize);

                if (!ret.getCerto() || b.checkwin(ret.getCerto())) {
                    mostrar_btn_continuar();
                    aviso.setText(ret.getAviso());
                    return false;
                }

                anim_dir.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        botoesdecima[posicao].setClickable(false);
                        botoesdebaixo[posicao].setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        botoesdecima[posicao + 1].setVisibility(View.VISIBLE);
                        botoesdecima[posicao].setVisibility(View.INVISIBLE);
                        botoesdecima[posicao + 1].setOnTouchListener(cimaesq);

                        botoesdebaixo[posicao + 1].setText("Baixo");
                        botoesdebaixo[posicao + 1].setVisibility(View.VISIBLE);
                        botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                        botoesdebaixo[posicao + 1].setOnTouchListener(baixoesq);

                        botoesdecima[posicao].setClickable(true);
                        botoesdebaixo[posicao].setClickable(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                botoesdebaixo[posicao].startAnimation(anim_dir);
                botoesdecima[posicao].startAnimation(anim_dir);
                return true;
            }
            return false;
        }
    };

    OnTouchListener cimadir = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (b.getPosicao() == 0) {
                    b.setPosicao(-5);
                    botoesdecima[0].setVisibility(View.INVISIBLE);
                    botoesdebaixo[0].setVisibility(View.INVISIBLE);
                }
                posicao = -b.getPosicao() - 1;
                Return2Valores ret = b.cima(b.getPosicao());
                x1[posicao].setText(Html.fromHtml(printCarta(b.Linha2[posicao])));

                anim_cartas();

                resize.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        resize_texview.setText(x1[posicao].getText());
                        resize_texview.setVisibility(View.VISIBLE);
                        x1[posicao].setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        resize_texview.setText("");
                        resize_texview.setVisibility(View.INVISIBLE);
                        x1[posicao].setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                resize_texview.startAnimation(resize);
                if (!ret.getCerto() || b.checkwin(ret.getCerto())) {
                    botoesdecima[posicao].setVisibility(View.INVISIBLE);
                    botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                    botoesdecima[posicao].setVisibility(View.INVISIBLE);
                    botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                    botoesdebaixo[2].setVisibility(View.VISIBLE);
                    botoesdebaixo[2].setText("Continuar");
                    botoesdebaixo[2].setOnTouchListener(continuar);
                    aviso.setText(ret.getAviso());
                    animacao_aviso();
                    b.setPosicao(0);
                    return false;
                }

                anim_esq.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        botoesdecima[posicao].setClickable(false);
                        botoesdebaixo[posicao].setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        botoesdecima[posicao - 1].setVisibility(View.VISIBLE);
                        botoesdecima[posicao].setVisibility(View.INVISIBLE);
                        botoesdecima[posicao - 1].setOnTouchListener(cimadir);

                        botoesdebaixo[posicao - 1].setText("Baixo");
                        botoesdebaixo[posicao - 1].setVisibility(View.VISIBLE);
                        botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                        botoesdebaixo[posicao - 1].setOnTouchListener(baixodir);

                        botoesdecima[posicao].setClickable(true);
                        botoesdebaixo[posicao].setClickable(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                botoesdebaixo[posicao].startAnimation(anim_esq);
                botoesdecima[posicao].startAnimation(anim_esq);
                return true;
            }
            return false;
        }
    };
    OnTouchListener baixodir = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (b.getPosicao() == 0) {
                    
                    
                    b.setPosicao(-5);
                    botoesdecima[0].setVisibility(View.INVISIBLE);
                    botoesdebaixo[0].setVisibility(View.INVISIBLE);
                }
                posicao = -b.getPosicao() - 1;
                Return2Valores ret = b.baixo(b.getPosicao());
                x1[posicao].setText(Html.fromHtml(printCarta(b.Linha2[posicao])));

                anim_cartas();

                resize.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        resize_texview.setText(x1[posicao].getText());
                        resize_texview.setVisibility(View.VISIBLE);
                        x1[posicao].setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        resize_texview.setText("");
                        resize_texview.setVisibility(View.INVISIBLE);
                        x1[posicao].setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                resize_texview.startAnimation(resize);
                if (!ret.getCerto() || b.checkwin(ret.getCerto())) {
                    botoesdecima[posicao].setVisibility(View.INVISIBLE);
                    botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                    botoesdecima[posicao].setVisibility(View.INVISIBLE);
                    botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                    botoesdebaixo[2].setVisibility(View.VISIBLE);
                    botoesdebaixo[2].setText("Continuar");
                    botoesdebaixo[2].setOnTouchListener(continuar);
                    aviso.setText(ret.getAviso());
                    animacao_aviso();
                    b.setPosicao(0);
                    return false;
                }

                anim_esq.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        botoesdecima[posicao].setClickable(false);
                        botoesdebaixo[posicao].setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        botoesdecima[posicao - 1].setVisibility(View.VISIBLE);
                        botoesdecima[posicao].setVisibility(View.INVISIBLE);
                        botoesdecima[posicao - 1].setOnTouchListener(cimadir);

                        botoesdebaixo[posicao - 1].setText("Baixo");
                        botoesdebaixo[posicao - 1].setVisibility(View.VISIBLE);
                        botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
                        botoesdebaixo[posicao - 1].setOnTouchListener(baixodir);

                        botoesdecima[posicao].setClickable(true);
                        botoesdebaixo[posicao].setClickable(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                botoesdebaixo[posicao].startAnimation(anim_esq);
                botoesdecima[posicao].startAnimation(anim_esq);
                return true;
            }
        return false;
        }
    };

    OnTouchListener continuar = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                b.organizar();
                for (int i = 0; i < 5; i++) {
                    if (x1[i].getText() != null) {
                        x[i].setText(Html.fromHtml(printCarta(b.Linha1[i])));
                        x1[i].setText(null);
                    }
                }
                botoesdebaixo[0].setOnTouchListener(baixoesq);
                botoesdebaixo[4].setOnTouchListener(baixodir);
                botoesdecima[0].setOnTouchListener(cimaesq);
                botoesdecima[4].setOnTouchListener(cimadir);
                botoesdecima[0].setVisibility(View.VISIBLE);
                botoesdebaixo[0].setVisibility(View.VISIBLE);
                botoesdecima[4].setVisibility(View.VISIBLE);
                botoesdebaixo[4].setVisibility(View.VISIBLE);
                botoesdebaixo[2].setVisibility(View.INVISIBLE);
                aviso.setVisibility(View.INVISIBLE);
                return true;
            }
            return false;
        }    
    };

    public void animacao_aviso() {
        anim_aviso.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                aviso.setVisibility(View.INVISIBLE);
                botoesdebaixo[2].setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                aviso.setVisibility(View.VISIBLE);
                botoesdebaixo[2].setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        aviso.startAnimation(anim_aviso);
    }

    public void mostrar_btn_continuar() {
        botoesdecima[posicao].setVisibility(View.INVISIBLE);
        botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
        botoesdecima[posicao].setVisibility(View.INVISIBLE);
        botoesdebaixo[posicao].setVisibility(View.INVISIBLE);
        botoesdebaixo[2].setText("Continuar");
        botoesdebaixo[2].setOnTouchListener(continuar);
        animacao_aviso();
        b.setPosicao(0);
    }

    public void anim_cartas() {
        switch (posicao) {
            case 0:
                resize = resize1;
                break;
            case 1:
                resize = resize2;
                break;
            case 2:
                resize = resize3;
                break;
            case 3:
                resize = resize4;
                break;
            case 4:
                resize = resize5;
                break;
        }
    }

    public String printCarta(Carta c) {
        String text = c.valor[c.getValores()];
        if (c.getNaipes() == 0) {
            text += "<font color=red>♦</font>";
        }
        if (c.getNaipes() == 1) {
            text += "<font color=red>♥</font>";
        }
        if (c.getNaipes() == 2) {
            text += "<font color=black>♣</font>";
        }
        if (c.getNaipes() == 3) {
            text += "<font color=black>♠</font>";
        }
        return text;
    }
}
