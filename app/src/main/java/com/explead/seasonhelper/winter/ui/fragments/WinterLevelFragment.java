package com.explead.seasonhelper.winter.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.app.App;
import com.explead.seasonhelper.common.beans.Level;
import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.common.ui.LevelsActivity;
import com.explead.seasonhelper.winter.genrate.GenerateContainer;
import com.explead.seasonhelper.winter.genrate.GeneratorField;
import com.explead.seasonhelper.winter.logic.WinterCell;
import com.explead.seasonhelper.winter.ui.winter_views.WinterCellView;
import com.explead.seasonhelper.winter.ui.winter_views.WinterCreateFieldView;

import java.util.ArrayList;

public class WinterLevelFragment extends Fragment implements WinterCreateFieldView.OnFieldCreateViewListener {

    private enum ValueCell {
        EMPTY, WALL,CUBE, INSIDE_CUBE, ARROW
    }

    private LevelsActivity activity;

    private static int number = 5;
    private ValueCell valueCell = ValueCell.EMPTY;
    private Cell.ColorCube color;
    private WinterCreateFieldView winterCreateFieldView;
    private TextView tvDirections;

    private ArrayList<GenerateContainer> containers;

    private LinearLayout seekBarLayout;
    private SeekBar seekBarContainers;

    private ImageView ivArrow;
    private int valueDirection = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_levels_winter, container, false);

        final SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                number = progress;
                winterCreateFieldView.removeAll();
                createField();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ivArrow = view.findViewById(R.id.ivArrow);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.ARROW;
            }
        });
        ivArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                valueDirection = (valueDirection + 1) % 4;
                for(Direction direction: Direction.values()) {
                    if(direction.getId() == valueDirection + 1) {
                        ivArrow.setRotation(direction.getRotation());
                    }
                }
                return false;
            }
        });

        seekBarLayout = view.findViewById(R.id.seekBarLayout);
        RelativeLayout white = view.findViewById(R.id.viewWhite);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.EMPTY;
            }
        });
        RelativeLayout wall = view.findViewById(R.id.viewWall);
        wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.WALL;
            }
        });
        RelativeLayout red = view.findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.CUBE;
                color = Cell.ColorCube.RED;
            }
        });
        RelativeLayout blue = view.findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.CUBE;
                color = Cell.ColorCube.BLUE;
            }
        });
        RelativeLayout yellow = view.findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.CUBE;
                color = Cell.ColorCube.YELLOW;
            }
        });
        RelativeLayout insideRed = view.findViewById(R.id.inside_red);
        insideRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.INSIDE_CUBE;
                color = Cell.ColorCube.RED;
            }
        });
        RelativeLayout insideBlue = view.findViewById(R.id.inside_blue);
        insideBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.INSIDE_CUBE;
                color = Cell.ColorCube.BLUE;
            }
        });
        RelativeLayout insideYellow = view.findViewById(R.id.inside_yellow);
        insideYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCell = ValueCell.INSIDE_CUBE;
                color = Cell.ColorCube.YELLOW;
            }
        });

        Button btnStartTest = view.findViewById(R.id.btnStartTest);

        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[][] mass = winterCreateFieldView.getMassField();
                App.setWinterContainer(winterCreateFieldView.getContainer());
                App.setWinterMass(mass);
                activity.openGameActivity(Level.WINTER, (String)tvDirections.getTag());
            }
        });

        Button btnGenerator = view.findViewById(R.id.btnGenerate);
        btnGenerator.setOnClickListener(generate);

        seekBarContainers = view.findViewById(R.id.seekBarContainers);
        seekBarContainers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setGenerateContainer(containers.get(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        TextView plus = view.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seekBarContainers.getProgress() < seekBarContainers.getMax()) {
                    seekBarContainers.setProgress(seekBarContainers.getProgress()+1);
                    setGenerateContainer(containers.get(seekBarContainers.getProgress()));
                }
            }
        });

        TextView minus = view.findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seekBarContainers.getProgress() > 0) {
                    seekBarContainers.setProgress(seekBarContainers.getProgress()-1);
                    setGenerateContainer(containers.get(seekBarContainers.getProgress()));
                }
            }
        });


        tvDirections = view.findViewById(R.id.tvDirections);
        tvDirections.setTag("Направлений нет!");

        winterCreateFieldView = view.findViewById(R.id.createFieldView);

        createField();

        return view;
    }

    private View.OnClickListener generate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GeneratorField generator = new GeneratorField(winterCreateFieldView.getField(), new GeneratorField.OnGenerateListener() {
                @Override
                public void onResult(ArrayList<GenerateContainer> result) {
                    containers = result;
                    seekBarLayout.setVisibility(View.VISIBLE);
                    seekBarContainers.setMax(result.size()-1);
                    seekBarContainers.setProgress(result.size()-1);
                    setGenerateContainer(result.get(seekBarContainers.getProgress()));
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
            generator.startGenerator(winterCreateFieldView.getCubes());
        }
    };

    private void setGenerateContainer(GenerateContainer container) {
        winterCreateFieldView.addOnFieldInsideCube(container.getCells());
        setStringInfoDirections(container.getDirections().size(), container.getStringDirections(), container.getAmount());
    }

    public void setStringInfoDirections(int steps, String directions, int amount) {
        String str = directions + "\nPos: " + String.valueOf(containers.size()) + " Steps: " +
                String.valueOf(steps) + " Amount: " + String.valueOf(amount);
        tvDirections.setText(str);
        tvDirections.setTag(directions);
    }

    public void createField() {
        winterCreateFieldView.createField(number);
        winterCreateFieldView.setOnFieldCreateViewListener(this);
    }

    @Override
    public void onCellClick(WinterCellView cell) {
        if(valueCell == ValueCell.EMPTY) {
            cell.getCell().changePurpose(WinterCell.PurposeCell.EMPTY);
            winterCreateFieldView.deleteOnThisCell(cell.getCell().getX(), cell.getCell().getY());
            winterCreateFieldView.deleteArrow(cell.getCell().getX(), cell.getCell().getY());
        } else if(valueCell == ValueCell.WALL) {
            cell.getCell().changePurpose(WinterCell.PurposeCell.WALL);
            winterCreateFieldView.deleteOnThisCell(cell.getCell().getX(), cell.getCell().getY());
            winterCreateFieldView.deleteArrow(cell.getCell().getX(), cell.getCell().getY());
        } else if(valueCell == ValueCell.CUBE) {
            winterCreateFieldView.createCube(cell.getCell().getX(), cell.getCell().getY(), color);
            winterCreateFieldView.deleteArrow(cell.getCell().getX(), cell.getCell().getY());
        } else if(valueCell == ValueCell.INSIDE_CUBE) {
            winterCreateFieldView.createInsideCube(cell.getCell().getX(), cell.getCell().getY(), color);
            winterCreateFieldView.deleteArrow(cell.getCell().getX(), cell.getCell().getY());
        } else if(valueCell == ValueCell.ARROW) {
            if(cell.getCell().getPurpose() == WinterCell.PurposeCell.EMPTY) {
                winterCreateFieldView.createArrow(cell.getCell().getX(), cell.getCell().getY(), valueDirection + 1);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (LevelsActivity)context;
    }
}
